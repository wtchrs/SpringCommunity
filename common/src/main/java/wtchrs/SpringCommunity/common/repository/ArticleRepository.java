package wtchrs.SpringCommunity.common.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import wtchrs.SpringCommunity.common.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findArticlesByAuthor_Id(Long userId, Pageable pageable);

    @EntityGraph(attributePaths = {"author"})
    Page<Article> findArticlesByBoard_Id(Long boardId, Pageable pageable);

    @EntityGraph(attributePaths = {"author", "board"})
    Page<Article> findAllBoardsArticlesBy(Pageable pageable);
}
