package wtchrs.SpringCommunity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import wtchrs.SpringCommunity.entity.article.Article;
import wtchrs.SpringCommunity.entity.article.ArticleRepository;
import wtchrs.SpringCommunity.entity.board.Board;
import wtchrs.SpringCommunity.entity.board.BoardRepository;
import wtchrs.SpringCommunity.entity.user.User;
import wtchrs.SpringCommunity.entity.user.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final UserRepository userRepository;

    private final BoardRepository boardRepository;

    private final ImageStoreService imageStoreService;

    @Transactional
    public Long post(Long userId, Long boardId, String title, String content, String articleToken) {
        Optional<User> findUser = userRepository.findById(userId);
        User user = findUser.orElseThrow(() -> new IllegalStateException("Not exist user id"));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalStateException("Not exist board id"));

        Article article = articleRepository.save(new Article(user, board, title, content, articleToken));
        return article.getId();
    }

    @Transactional
    public void editArticle(Long articleId, String title, String content) {
        // TODO: edit images(add, remove)

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        article.editArticle(title, content);
    }

    public Page<Article> getAllBoardsArticles(Pageable pageable) {
        return articleRepository.findAllBoardsArticlesBy(pageable);
    }

    public Page<Article> getBoardArticles(Long boardId, Pageable pageable) {
        return articleRepository.findArticlesByBoard_Id(boardId, pageable);
    }

    public Page<Article> getUserArticles(Long userId, Pageable pageable) {
        return articleRepository.findArticlesByAuthor_Id(userId, pageable);
    }

    public Long getBoardIdFromArticle(Long articleId) {
        return articleRepository.findBoardIdById(articleId);
    }

    @Transactional
    public void deleteArticle(Long articleId, User user) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!article.getAuthor().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        articleRepository.delete(article);
    }

    @Transactional
    public Article viewContent(Long articleId) {
        Article article = articleRepository.findArticleById(articleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        article.increaseViewCount();
        return article;
    }
}
