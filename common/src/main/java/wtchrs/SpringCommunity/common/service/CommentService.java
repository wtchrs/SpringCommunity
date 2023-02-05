package wtchrs.SpringCommunity.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wtchrs.SpringCommunity.common.entity.Article;
import wtchrs.SpringCommunity.common.entity.Comment;
import wtchrs.SpringCommunity.common.entity.User;
import wtchrs.SpringCommunity.common.repository.ArticleRepository;
import wtchrs.SpringCommunity.common.repository.CommentRepository;
import wtchrs.SpringCommunity.common.repository.UserRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    /**
     * @param userId    pk value of user.
     * @param articleId pk value of article.
     * @param content   content of the comment.
     * @return pk value of comment.
     * @throws IllegalStateException when the user or article is not exist.
     */
    @Transactional
    public Long comment(Long userId, Long articleId, String content) {
        Optional<User> findUser = userRepository.findById(userId);
        User user = findUser.orElseThrow(() -> new IllegalStateException("Not exist user id"));

        Optional<Article> findArticle = articleRepository.findById(articleId);
        Article article = findArticle.orElseThrow(() -> new IllegalStateException("Not exist article id"));

        Comment comment = new Comment(user, article, content);
        commentRepository.save(comment);
        return comment.getId();
    }

    /**
     * @param commentId pk value of comment to delete.
     */
    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    /**
     * @param userId pk value of user.
     * @return first page of comments.
     */
    public Page<Comment> getUserComments(Long userId) {
        return getUserComments(userId, PageRequest.of(0, 20));
    }

    /**
     * @param userId   pk value of author.
     * @param pageable page info to find.
     * @return page of articles.
     */
    public Page<Comment> getUserComments(Long userId, Pageable pageable) {
        return commentRepository.findCommentsByUser_Id(userId, pageable);
    }

    /**
     * @param articleId pk value of article.
     * @return first page of comments.
     */
    public Page<Comment> getArticleComments(Long articleId) {
        return getArticleComments(articleId, PageRequest.of(0, 20));
    }

    /**
     * @param articleId pk value of article.
     * @return page of comments.
     */
    public Page<Comment> getArticleComments(Long articleId, Pageable pageable) {
        return commentRepository.findCommentsByArticle_Id(articleId, pageable);
    }
}
