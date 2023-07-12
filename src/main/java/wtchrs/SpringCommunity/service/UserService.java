package wtchrs.SpringCommunity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wtchrs.SpringCommunity.auth.UserAuth;
import wtchrs.SpringCommunity.entity.board.BoardAdminRepository;
import wtchrs.SpringCommunity.entity.user.NameHistory;
import wtchrs.SpringCommunity.entity.user.NameHistoryRepository;
import wtchrs.SpringCommunity.entity.user.User;
import wtchrs.SpringCommunity.entity.user.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final NameHistoryRepository nameHistoryRepository;

    private final BoardAdminRepository boardAdminRepository;

    @Transactional
    public Long signUp(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalStateException("Already exist username");
        }
        userRepository.save(user);
        return user.getId();
    }

    /**
     * @param username Username.
     * @param password Password.
     * @return {@link UserAuth} for user.
     * @throws IllegalStateException when username or password is wrong.
     */
    public UserAuth signIn(String username, String password) {
        Optional<User> findUser = userRepository.findByUsername(username);

        if (findUser.isEmpty()) return null;
        User user = findUser.get();

        if (!user.checkPassword(password)) return null;
        return new UserAuth(user.getId(), user.getUsername());
    }

    @Transactional
    public void changePassword(Long userId, String currentPasswd, String newPasswd, String confirmPasswd) {
        Optional<User> findUser = userRepository.findById(userId);
        User user = findUser.orElseThrow(() -> new IllegalStateException("Not exist user id"));

        user.changePassword(currentPasswd, newPasswd, confirmPasswd);
    }

    @Transactional
    public void changeName(Long userId, String newName) {
        Optional<User> findUser = userRepository.findById(userId);
        User user = findUser.orElseThrow(() -> new IllegalStateException("Not exist user id"));

        NameHistory nameHistory = user.changeName(newName);
        nameHistoryRepository.save(nameHistory);
    }
}
