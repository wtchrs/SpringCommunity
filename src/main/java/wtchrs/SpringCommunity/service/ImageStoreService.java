package wtchrs.SpringCommunity.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import wtchrs.SpringCommunity.entity.image.ImageContent;
import wtchrs.SpringCommunity.entity.image.ImageContentRepository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageStoreService {

    public static final String RESIZED_IMAGE_NAME_POSTFIX = "-720";

    public static final String IMAGE_URL_PREFIX = "/image/";

    private final ImageContentRepository imageContentRepository;

    private final ImageResizeService imageResizeService;

    @Value("${file.image.dir}")
    private String imageDir;

    @Value("${file.image.filename.length}")
    private int filenameLength;

    private String getFullPath(String filename) {
        return imageDir + filename;
    }

    public String getResizedImageUrl(ImageContent image) {
        return IMAGE_URL_PREFIX + getFilename(image, true);
    }

    public String getFilename(ImageContent image, boolean resized) {
        if (resized) {
            return image.getName() + RESIZED_IMAGE_NAME_POSTFIX + ".jpg";
        } else {
            return image.getName() + "." + image.getExtension();
        }
    }

    @Transactional
    public ImageContent storeImage(MultipartFile image, String articleToken) {
        String originalFilename = image.getOriginalFilename();
        if (originalFilename == null) throw new IllegalArgumentException();

        String randomName = createRandomString();
        String ext = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);

        try {
            BufferedImage originalImage = ImageIO.read(image.getInputStream());
            BufferedImage resizedImage = imageResizeService.resize(originalImage);
            ImageIO.write(originalImage, ext, new File(getFullPath(randomName + "." + ext)));
            ImageIO.write(resizedImage, "jpg", new File(getFullPath(randomName + RESIZED_IMAGE_NAME_POSTFIX + ".jpg")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return imageContentRepository.save(new ImageContent(randomName, ext, articleToken));
    }

    private String createRandomString() {
        int leftLimit = '0';
        int rightLimit = 'z';
        return new Random().ints(leftLimit, rightLimit + 1)
                .filter(Character::isLetterOrDigit)
                .limit(filenameLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
