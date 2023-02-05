package wtchrs.SpringCommunity.common.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ImageContent extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "image_content_id")
    private Long id;

    @Lob
    private byte[] content;

    @Enumerated(EnumType.STRING)
    private ImageContentType contentType;

    private int contentSize;

    // TODO: Factory method or constructor: create a ProfileImage instance out of the image file.
}
