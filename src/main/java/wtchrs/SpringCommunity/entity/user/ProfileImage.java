package wtchrs.SpringCommunity.entity.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wtchrs.SpringCommunity.entity.BaseEntity;
import wtchrs.SpringCommunity.entity.image.ImageContent;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProfileImage extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_content_id")
    private ImageContent imageContent;

    // TODO: Factory method or constructor: create a ProfileImage instance out of the image file.
}
