package wtchrs.SpringCommunity.common.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ImageContentType {
    JPG("image/jpg"), JPEG("image/jpeg"), PNG("image/png"), GIF("image/gif"), WEBP("image/webp");

    private final String contentType;
}
