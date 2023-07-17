package wtchrs.SpringCommunity.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

//    private final UserRepository userRepository;
//    private final BoardRepository boardRepository;
//    private final ArticleRepository articleRepository;
//
//    private final UserService userService;
//    private final BoardService boardService;
//    private final ArticleService articleService;
//    private final CommentService commentService;
//
//    @PostConstruct
//    public void createInitialData() {
//        Optional<User> findUser = userRepository.findByUsername("username");
//        User user;
//        if (findUser.isPresent()) {
//            user = findUser.get();
//        } else {
//            user = new User("username", "password", "hello");
//            userService.signUp(user);
//        }
//
//        Long firstBoardId;
//
//        for (int i = 0; i < 20; i++) {
//            String boardName = "board_" + i;
//            if (!boardRepository.existsByName(boardName)) {
//                boardService.createBoard(user.getId(), boardName);
//            }
//        }
//
//        Optional<Board> findFirstBoard = boardRepository.findByName("board_0");
//        firstBoardId = findFirstBoard.orElseThrow().getId();
//
//        if (articleRepository.count() == 0) {
//            Long articleId =
//                articleService.post(user.getId(), firstBoardId, "Hello!",
//                                    "This is the first article of this community!");
//            articleService.post(user.getId(), firstBoardId, "Second Article", "content");
//            articleService.post(user.getId(), firstBoardId,
//                                "Test title length Test title length Test title length Test title length Test title length title length title length title length title length",
//                                "content");
//            for (int i = 0; i < 20; i++) {
//                articleService.post(user.getId(), firstBoardId, "article_" + i, "content");
//            }
//
//            commentService.comment(user.getId(), articleId, "And this is the first comment.");
//            commentService.comment(user.getId(), articleId, "second comment");
//
//            for (int i = 0; i < 20; i++) {
//                commentService.comment(user.getId(), articleId, "comment_" + i);
//            }
//        }
//    }
}
