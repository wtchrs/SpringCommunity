package wtchrs.SpringCommunity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import wtchrs.SpringCommunity.entity.article.Article;
import wtchrs.SpringCommunity.entity.article.ArticleRepository;
import wtchrs.SpringCommunity.entity.image.ImageContent;
import wtchrs.SpringCommunity.entity.image.ImageContentRepository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageStoreService {

    private final ImageContentRepository imageContentRepository;
    private final ArticleRepository articleRepository;

    private final ImageResizeService imageResizeService;

    @Value("${file.image.dir}")
    private String imageDir;

    @Value("${file.image.resized-image-postfix}")
    private String postfix;

    public String getFullPath(String filename) {
        return imageDir + filename;
    }

    public String getResizedImageUrl(ImageContent image) {
        String url = "/images/" + image.getStoredFilename() + "/" + image.getOriginalFilename();
        if (image.isResized()) {
            int extPos = url.lastIndexOf(".");
            url = url.substring(0, extPos) + postfix + ".jpg";
        }
        return url;
    }

    @Transactional
    public List<ImageContent> storeArticleImages(Long articleId, List<MultipartFile> images) {
        Optional<Article> findArticle = articleRepository.findArticleById(articleId);
        Article article = findArticle.orElseThrow(() -> new IllegalStateException("Not exist article id"));

        return images.stream().map(image -> storeArticleImageProcess(article, image)).toList();
    }

    @Transactional
    public ImageContent storeArticleImage(Long articleId, MultipartFile image) {
        Optional<Article> findArticle = articleRepository.findArticleById(articleId);
        Article article = findArticle.orElseThrow(() -> new IllegalStateException("Not exist article id"));

        return storeArticleImageProcess(article, image);
    }

    private ImageContent storeArticleImageProcess(Article article, MultipartFile image) {
        String originalFilename = image.getOriginalFilename();
        if (originalFilename == null) return null;

        String storedFilename = UUID.randomUUID().toString();
        String ext = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
        boolean resized = false;
        try {
            BufferedImage originalImage = ImageIO.read(image.getInputStream());
            BufferedImage resizedImage = imageResizeService.resize(originalImage);
            if (!resizedImage.equals(originalImage)) {
                ImageIO.write(resizedImage, "jpg", new File(getFullPath(storedFilename + postfix)));
                resized = true;
            }
            ImageIO.write(originalImage, "jpg", new File(getFullPath(storedFilename)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return imageContentRepository.save(new ImageContent(article, originalFilename, storedFilename, ext, resized));
    }
}
