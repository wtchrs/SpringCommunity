package wtchrs.SpringCommunity.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import wtchrs.SpringCommunity.controller.response.ImageUploadResponse;
import wtchrs.SpringCommunity.entity.image.ImageContent;
import wtchrs.SpringCommunity.service.ImageStoreService;

@RestController
@RequiredArgsConstructor
public class ImageUploadController {

    private final ImageStoreService imageStoreService;

    @PostMapping("/api/images/upload")
    public ImageUploadResponse upload(
            CsrfToken csrfToken, @RequestParam(required = false) String articleToken, MultipartFile image) {
        String token = StringUtils.hasText(articleToken) ? articleToken : csrfToken.getToken();
        ImageContent imageContent = imageStoreService.storeImage(image, token);
        String imageUrl = imageStoreService.getResizedImageUrl(imageContent);
        return new ImageUploadResponse(imageUrl);
    }

}
