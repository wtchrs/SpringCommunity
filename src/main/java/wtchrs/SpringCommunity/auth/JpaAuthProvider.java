package wtchrs.SpringCommunity.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import wtchrs.SpringCommunity.entity.user.User;
import wtchrs.SpringCommunity.service.JpaUserDetailsManager;

@Component
@RequiredArgsConstructor
@Slf4j
public class JpaAuthProvider implements AuthenticationProvider {

    private final JpaUserDetailsManager userDetailsManager;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("[JpaAuthProvider.authenticate] username = {}", authentication.getName());
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        User user = (User) userDetailsManager.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Wrong username or password.");
        }
        return new JpaAuthenticate(user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
