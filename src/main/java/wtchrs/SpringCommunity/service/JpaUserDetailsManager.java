package wtchrs.SpringCommunity.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wtchrs.SpringCommunity.auth.JpaAuthenticate;
import wtchrs.SpringCommunity.entity.user.User;
import wtchrs.SpringCommunity.entity.user.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class JpaUserDetailsManager implements UserDetailsManager {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    // TODO: Write this class and replace session login(service.UserService) with this class.

    /**
     * @param userDetails must be an instance of {@link User}.
     * @throws IllegalStateException if the username is duplicated.
     */
    @Override
    @Transactional
    public void createUser(UserDetails userDetails) {
        User user = (User) userDetails;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userRepository.existsByUsername(userDetails.getUsername())) {
            throw new IllegalStateException("Already exist username");
        }
        user.addAuthority("WRITE");
        userRepository.save(user);
    }

    /**
     * @throws UnsupportedOperationException This method is not supported.
     */
    @Override
    public void updateUser(UserDetails user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteUser(String username) {
        // TODO
    }

    /**
     * @param oldPassword current password
     * @param newPassword the password to change to
     * @throws BadCredentialsException if oldPassword is not correct.
     */
    @Override
    public void changePassword(String oldPassword, String newPassword) {
        JpaAuthenticate auth = (JpaAuthenticate) SecurityContextHolder.getContext().getAuthentication();
        User user = auth.getUser();
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BadCredentialsException("Not matched password.");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("[JpaUserDetailsManager.loadUserByUsername] username = {}", username);
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
