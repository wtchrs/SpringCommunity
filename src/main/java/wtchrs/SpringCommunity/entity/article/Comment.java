package wtchrs.SpringCommunity.entity.article;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wtchrs.SpringCommunity.entity.BaseEntity;
import wtchrs.SpringCommunity.entity.user.User;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", updatable = false)
    private Article article;

    @Lob
    private String content;

    public Comment(User user, Article article, String content) {
        this.user = user;
        this.article = article;
        this.content = content;
    }

}
