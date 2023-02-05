package wtchrs.SpringCommunity.common.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProfileImage extends BaseEntity {

    @EmbeddedId
    private ProfileImageId profileImageId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_content_id")
    private ImageContent imageContent;
}
