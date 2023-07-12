package wtchrs.SpringCommunity.entity.image;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wtchrs.SpringCommunity.entity.BaseEntity;
import wtchrs.SpringCommunity.entity.article.Article;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageContent extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "image_content_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", updatable = false)
    private Article article;

    @Column(updatable = false)
    private String originalFilename;
    @Column(updatable = false)
    private String storedFilename;
    @Column(updatable = false, length = 4)
    private String imageType;

    private boolean resized;

    public ImageContent(Article article, String originalFilename, String storedFilename, String ext, boolean resized) {
        this.article = article;
        this.originalFilename = originalFilename;
        this.storedFilename = storedFilename;
        this.imageType = imageTypeFromExt(ext);
        this.resized = resized;
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
