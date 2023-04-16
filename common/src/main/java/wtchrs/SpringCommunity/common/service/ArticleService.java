package wtchrs.SpringCommunity.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wtchrs.SpringCommunity.common.entity.article.Article;
import wtchrs.SpringCommunity.common.entity.article.ArticleRepository;
import wtchrs.SpringCommunity.common.entity.board.Board;
import wtchrs.SpringCommunity.common.entity.board.BoardRepository;
import wtchrs.SpringCommunity.common.entity.user.User;
import wtchrs.SpringCommunity.common.entity.user.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public Long post(Long userId, Long boardId, String title, String content) {
        Optional<User> findUser = userRepository.findById(userId);
        User user = findUser.orElseThrow(() -> new IllegalStateException("Not exist user id"));

        Optional<Board> findBoard = boardRepository.findById(boardId);
        Board board = findBoard.orElseThrow(() -> new IllegalStateException("Not exist board id"));

        Article article = new Article(user, board, title, content);
        articleRepository.save(article);

        return article.getId();
    }

    @Transactional
    public void editArticle(Long articleId, String title, String content) {
        Optional<Article> findArticle = articleRepository.findById(articleId);
        Article article = findArticle.orElseThrow(() -> new IllegalStateException("Not exist article id"));

        article.editArticle(title, content);
    }

    public Page<Article> getAllBoardsArticles(Pageable pageable) {
        return articleRepository.findAllBoardsArticlesBy(pageable);
    }

    public Page<Article> getBoardArticles(Long boardId) {
        return getBoardArticles(boardId, PageRequest.of(0, 20));
    }

    public Page<Article> getBoardArticles(Long boardId, Pageable pageable) {
        return articleRepository.findArticlesByBoard_Id(boardId, pageable);
    }

    public Page<Article> getUserArticles(Long userId) {
        return getUserArticles(userId, PageRequest.of(0, 20));
    }

    public Page<Article> getUserArticles(Long userId, Pageable pageable) {
        return articleRepository.findArticlesByAuthor_Id(userId, pageable);
    }

    public Long getBoardIdFromArticle(Long articleId) {
        return articleRepository.findBoardIdById(articleId);
    }

    @Transactional
    public Article viewContent(Long articleId) {
        Optional<Article> findArticle = articleRepository.findArticleById(articleId);
        Article article = findArticle.orElseThrow(() -> new IllegalStateException("Not exist article id"));
        article.increaseViewCount();
        return article;
    }
}
