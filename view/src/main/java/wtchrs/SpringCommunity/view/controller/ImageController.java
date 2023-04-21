package wtchrs.SpringCommunity.view.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import wtchrs.SpringCommunity.common.entity.image.ImageContent;
import wtchrs.SpringCommunity.common.entity.image.ImageContentRepository;
import wtchrs.SpringCommunity.common.service.ImageStoreService;

import java.net.MalformedURLException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageStoreService imageStoreService;
    private final ImageContentRepository imageContentRepository;

    @GetMapping("/images/{stored}/{original}")
    public ResponseEntity<Resource> articleImage(@PathVariable String stored, @PathVariable String original) {
        Optional<ImageContent> findImageContent = imageContentRepository.findByStoredFilename(stored);
        if (findImageContent.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        ImageContent imageContent = findImageContent.get();

        if (!imageContent.getOriginalFilename().equals(original)) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", "/images/" + stored + "/" + imageContent.getOriginalFilename())
                    .body(null);
        }

        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf("image/" + imageContent.getImageType()))
                    .body(new UrlResource("file:" + imageStoreService.getFullPath(stored)));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
