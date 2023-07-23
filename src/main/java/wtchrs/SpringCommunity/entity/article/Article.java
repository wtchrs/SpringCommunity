package wtchrs.SpringCommunity.entity.article;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wtchrs.SpringCommunity.entity.BaseEntity;
import wtchrs.SpringCommunity.entity.board.Board;
import wtchrs.SpringCommunity.entity.user.User;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Article extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "article_id")
    private Long id;

    @Column(updatable = false)
    private String articleToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", updatable = false)
    private Board board;

    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    private int viewCount;

    public Article(User author, Board board, String title, String content, String articleToken) {
        this.author = author;
        this.board = board;
        this.title = title;
        this.content = content;
        this.articleToken = articleToken;
    }

    public void editArticle(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void increaseViewCount() {
        this.viewCount++;
    }

}
