package wtchrs.SpringCommunity.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wtchrs.SpringCommunity.common.entity.Article;
import wtchrs.SpringCommunity.common.entity.Board;
import wtchrs.SpringCommunity.common.entity.User;
import wtchrs.SpringCommunity.common.repository.ArticleRepository;
import wtchrs.SpringCommunity.common.repository.BoardRepository;
import wtchrs.SpringCommunity.common.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    /**
     * @param userId  pk value of user that is author of the posted article.
     * @param boardId pk value of board.
     * @param title   article title.
     * @param content article content.
     * @return pk value of the posted article.
     * @throws IllegalStateException when the user or board is not exist.
     */
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

    /**
     * @param articleId pk value of article.
     * @param title     new title string.
     * @param content   new content string.
     * @throws IllegalStateException when the article is not exist.
     */
    @Transactional
    public void editArticle(Long articleId, String title, String content) {
        Optional<Article> findArticle = articleRepository.findById(articleId);
        Article article = findArticle.orElseThrow(() -> new IllegalStateException("Not exist article id"));

        article.editArticle(title, content);
    }

    public Page<Article> getAllBoardsArticles(Pageable pageable) {
        return articleRepository.findAllBoardsArticlesBy(pageable);
    }

    /**
     * @param boardId pk value of board.
     * @return first page of articles of the board.
     */
    public Page<Article> getBoardArticles(Long boardId) {
        return getBoardArticles(boardId, PageRequest.of(0, 20));
    }

    /**
     * @param boardId  pk value of board.
     * @param pageable page info to find.
     * @return page of articles of the board.
     */
    public Page<Article> getBoardArticles(Long boardId, Pageable pageable) {
        return articleRepository.findArticlesByBoard_Id(boardId, pageable);
    }

    /**
     * @param userId pk value of author.
     * @return first page of articles.
     * @throws IllegalStateException when the user id passed is not exist.
     */
    public Page<Article> getUserArticles(Long userId) {
        return getUserArticles(userId, PageRequest.of(0, 20));
    }

    /**
     * @param userId   pk value of author.
     * @param pageable page info to find.
     * @return page of articles.
     * @throws IllegalStateException when the user id passed is not exist.
     */
    public Page<Article> getUserArticles(Long userId, Pageable pageable) {
        return articleRepository.findArticlesByAuthor_Id(userId, pageable);
    }

    /**
     * This method finds the article with {@code articleId}. If the article exists, it increases view count and return
     * the instance of article. If the article does not exist, it throws an {@link IllegalStateException}.
     *
     * @param articleId pk value of article.
     * @return instance of {@link Article}.
     * @throws IllegalStateException when the article is not exist.
     */
    @Transactional
    public Article viewContent(Long articleId) {
        Optional<Article> findArticle = articleRepository.findById(articleId);
        Article article = findArticle.orElseThrow(() -> new IllegalStateException("Not exist article id"));
        article.increaseViewCount();
        return article;
    }
}
