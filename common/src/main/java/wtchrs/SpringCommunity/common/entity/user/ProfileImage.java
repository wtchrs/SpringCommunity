package wtchrs.SpringCommunity.common.entity.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wtchrs.SpringCommunity.common.entity.BaseEntity;
import wtchrs.SpringCommunity.common.entity.ImageContent;

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
