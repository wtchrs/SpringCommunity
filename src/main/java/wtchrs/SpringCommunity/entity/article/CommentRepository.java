package wtchrs.SpringCommunity.entity.article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findCommentsByUser_Id(Long userId, Pageable pageable);

    Page<Comment> findCommentsByArticle_Id(Long articleId, Pageable pageable);

}
