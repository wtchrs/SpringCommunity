package wtchrs.community.common.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import wtchrs.community.common.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findArticlesByAuthor_Id(Long userId, Pageable pageable);

    Page<Article> findArticlesByBoard_Id(Long boardId, Pageable pageable);
}
