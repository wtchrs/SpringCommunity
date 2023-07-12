package wtchrs.SpringCommunity.response;

import lombok.Builder;
import lombok.Getter;
import wtchrs.SpringCommunity.entity.article.Article;
import wtchrs.SpringCommunity.entity.board.Board;
import wtchrs.SpringCommunity.entity.user.User;

import java.time.LocalDateTime;

@Getter
public class ArticleResponse {

    private final Long id;

    private final Long boardId;
    private final String boardName;

    private final Long authorId;
    private final String authorUsername;

    private final String title;
    private final String content;
    private final int viewCount;

    private final LocalDateTime createdDate;
    private final LocalDateTime lastModifiedDate;

    @Builder
    public ArticleResponse(
            Long id, Long boardId, String boardName, Long authorId, String authorUsername, String title, String content,
            int viewCount, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {

        this.id = id;
        this.boardId = boardId;
        this.boardName = boardName;
        this.authorId = authorId;
        this.authorUsername = authorUsername;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public static ArticleResponse of(Article article) {
        Board board = article.getBoard();
        User author = article.getAuthor();

        return ArticleResponse
                .builder().id(article.getId())
                .boardId(board.getId()).boardName(board.getName())
                .authorId(author.getId()).authorUsername(author.getUsername())
                .title(article.getTitle()).content(article.getContent()).viewCount(article.getViewCount())
                .createdDate(article.getCreatedDate()).lastModifiedDate(article.getLastModifiedDate())
                .build();
    }
}
