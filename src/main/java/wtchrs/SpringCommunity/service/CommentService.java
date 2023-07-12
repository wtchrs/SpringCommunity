package wtchrs.SpringCommunity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wtchrs.SpringCommunity.entity.article.Article;
import wtchrs.SpringCommunity.entity.article.ArticleRepository;
import wtchrs.SpringCommunity.entity.article.Comment;
import wtchrs.SpringCommunity.entity.article.CommentRepository;
import wtchrs.SpringCommunity.entity.user.User;
import wtchrs.SpringCommunity.entity.user.UserRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

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

    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public Page<Comment> getUserComments(Long userId) {
        return getUserComments(userId, PageRequest.of(0, 20));
    }

    public Page<Comment> getUserComments(Long userId, Pageable pageable) {
        return commentRepository.findCommentsByUser_Id(userId, pageable);
    }

    public Page<Comment> getArticleComments(Long articleId) {
        return getArticleComments(articleId, PageRequest.of(0, 20));
    }

    public Page<Comment> getArticleComments(Long articleId, Pageable pageable) {
        return commentRepository.findCommentsByArticle_Id(articleId, pageable);
    }
}
