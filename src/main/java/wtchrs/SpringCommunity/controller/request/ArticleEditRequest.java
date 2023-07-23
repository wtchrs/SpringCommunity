package wtchrs.SpringCommunity.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wtchrs.SpringCommunity.entity.article.Article;

@Getter
@Setter
@NoArgsConstructor
public class ArticleEditRequest {

    @NotBlank
    @Size(min = 5)
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String articleToken;

    private ArticleEditRequest(String title, String content, String articleToken) {
        this.title = title;
        this.content = content;
        this.articleToken = articleToken;
    }

    public static ArticleEditRequest of(Article article) {
        // TODO: implementation
        return new ArticleEditRequest(article.getTitle(), article.getContent(), article.getArticleToken());
    }

}
