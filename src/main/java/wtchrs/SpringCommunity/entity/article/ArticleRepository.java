package wtchrs.SpringCommunity.entity.article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findArticlesByAuthor_Id(Long userId, Pageable pageable);

    @EntityGraph(attributePaths = {"author"})
    Page<Article> findArticlesByBoard_Id(Long boardId, Pageable pageable);

    @EntityGraph(attributePaths = {"author", "board"})
    Page<Article> findAllBoardsArticlesBy(Pageable pageable);

    @Query("select a.board.id from Article a where a.id = :id")
    Long findBoardIdById(@Param("id") Long articleId);

    @EntityGraph(attributePaths = {"author", "board", "images"})
    Optional<Article> findArticleById(Long articleId);
}
