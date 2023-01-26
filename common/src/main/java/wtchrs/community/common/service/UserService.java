package wtchrs.community.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wtchrs.community.common.UserAuth;
import wtchrs.community.common.entity.Article;
import wtchrs.community.common.entity.Comment;
import wtchrs.community.common.entity.NameHistory;
import wtchrs.community.common.entity.User;
import wtchrs.community.common.repository.*;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final NameHistoryRepository nameHistoryRepository;
    private final ProfileImageRepository profileImageRepository;

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final BoardAdminRepository boardAdminRepository;

    /**
     * @param user user instance to sign up.
     * @return pk value of the user that signed up.
     * @throws IllegalStateException when {@code user.username} is already exist.
     */
    @Transactional
    public Long signup(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalStateException("Already exist username");
        }

        userRepository.save(user);
        return user.getId();
    }

    /**
     * @param username username.
     * @param password password.
     * @return the authentication information.
     * @throws IllegalStateException when wrong username or password is passed.
     */
    public UserAuth signin(String username, String password) {
        Optional<User> findUser = userRepository.findByUsername(username);
        User user = findUser.orElseThrow(() -> new IllegalStateException("Wrong username or password"));
        if (!user.checkPassword(password)) throw new IllegalStateException("Wrong username or password");

        return new UserAuth(user.getId());
    }

    /**
     * @param userId        pk value to change password.
     * @param currentPasswd current password of user.
     * @param newPasswd     new password to change.
     * @param confirmPasswd confirmation string.
     * @throws IllegalStateException    when the user id passed is not exist or wrong password is passed.
     * @throws IllegalArgumentException when {@code newPasswd} and {@code confirmPasswd} are not equal.
     */
    @Transactional
    public void changePassword(Long userId, String currentPasswd, String newPasswd, String confirmPasswd) {
        Optional<User> findUser = userRepository.findById(userId);
        User user = findUser.orElseThrow(() -> new IllegalStateException("Not exist user id"));

        user.changePassword(currentPasswd, newPasswd, confirmPasswd);
    }

    /**
     * @param userId  pk value to change name.
     * @param newName new name.
     * @throws IllegalStateException when the user id passed is not exist.
     */
    @Transactional
    public void changeName(Long userId, String newName) {
        Optional<User> findUser = userRepository.findById(userId);
        User user = findUser.orElseThrow(() -> new IllegalStateException("Not exist user id"));

        NameHistory nameHistory = user.changeName(newName);
        nameHistoryRepository.save(nameHistory);
    }

    /**
     * @param userId pk value of author.
     * @return first page of articles.
     * @throws IllegalStateException when the user id passed is not exist.
     */
    public Page<Article> getArticles(Long userId) {
        return getArticles(userId, PageRequest.of(0, 20));
    }

    /**
     * @param userId   pk value of author.
     * @param pageable page info to find.
     * @return page of articles.
     * @throws IllegalStateException when the user id passed is not exist.
     */
    public Page<Article> getArticles(Long userId, Pageable pageable) {
        return articleRepository.findArticlesByAuthor_Id(userId, pageable);
    }

    /**
     * @param userId pk value of user.
     * @return first page of comments.
     */
    public Page<Comment> getComments(Long userId) {
        return getComments(userId, PageRequest.of(0, 20));
    }

    /**
     * @param userId   pk value of author.
     * @param pageable page info to find.
     * @return page of articles.
     */
    public Page<Comment> getComments(Long userId, Pageable pageable) {
        return commentRepository.findCommentsByUser_Id(userId, pageable);
    }
}
