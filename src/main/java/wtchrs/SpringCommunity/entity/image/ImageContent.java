package wtchrs.SpringCommunity.entity.image;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wtchrs.SpringCommunity.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageContent extends BaseEntity {

    // TODO: remove originalFilename and use just storedFilename(and it will be renamed to 'filename').

    @Id
    @GeneratedValue
    @Column(name = "image_content_id")
    private Long id;

    /**
     * This column will be used to identify and remove images that have not been used since they were saved.
     */
    @Column(updatable = false)
    private String articleToken;

    @Column(updatable = false)
    private String name;

    @Column(updatable = false, length = 4)
    private String extension;

    public ImageContent(String name, String extension, String articleToken) {
        this.name = name;
        this.extension = extension;
        this.articleToken = articleToken;
    }

    public String getOriginalFilename() {
        return name + "." + extension;
    }

    private static String imageTypeFromExt(String ext) {
        ext = ext.toLowerCase();
        String type;
        switch (ext) {
            case "jpg", "jpeg" -> type = "jpeg";
            case "png" -> type = "png";
            case "gif" -> type = "gif";
            default -> throw new IllegalStateException("Unexpected value: " + ext);
        }
        return type;
    }

}
