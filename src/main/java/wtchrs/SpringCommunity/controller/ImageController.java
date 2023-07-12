package wtchrs.SpringCommunity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import wtchrs.SpringCommunity.entity.image.ImageContent;
import wtchrs.SpringCommunity.entity.image.ImageContentRepository;
import wtchrs.SpringCommunity.service.ImageStoreService;

import java.net.MalformedURLException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageStoreService imageStoreService;
    private final ImageContentRepository imageContentRepository;

    @Value("${file.image.resized-image-postfix}")
    private String postfix;

    @GetMapping("/images/{stored}/{original}")
    public ResponseEntity<Resource> articleImage(@PathVariable String stored, @PathVariable String original) {
        Optional<ImageContent> findImageContent = imageContentRepository.findByStoredFilename(stored);
        if (findImageContent.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        ImageContent imageContent = findImageContent.get();

        String originalFilename = imageContent.getOriginalFilename();
        String storedFilename = stored;
        if (original.endsWith(postfix + ".jpg")) {
            int extPos = originalFilename.lastIndexOf(".");
            originalFilename = originalFilename.substring(0, extPos) + postfix + ".jpg";
            storedFilename += postfix;
        }

        if (!originalFilename.equals(original)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf("image/" + imageContent.getImageType()))
                    .body(new UrlResource("file:" + imageStoreService.getFullPath(storedFilename)));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
