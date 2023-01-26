package wtchrs.community.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wtchrs.community.common.entity.Article;
import wtchrs.community.common.entity.Board;
import wtchrs.community.common.entity.User;
import wtchrs.community.common.repository.ArticleRepository;
import wtchrs.community.common.repository.BoardRepository;
import wtchrs.community.common.repository.UserRepository;

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
    public Long post(Long userId, Long boardId, String title, String content) {
        Optional<User> findUser = userRepository.findById(userId);
        User user = findUser.orElseThrow(() -> new IllegalStateException("Not exist user id"));

        Optional<Board> findBoard = boardRepository.findById(boardId);
        Board board = findBoard.orElseThrow(() -> new IllegalStateException("Not exist board id"));

        Article article = new Article(user, board, title, content);
        articleRepository.save(article);

        return article.getId();
    }

    // TODO: Edit, view, list, ...
}
