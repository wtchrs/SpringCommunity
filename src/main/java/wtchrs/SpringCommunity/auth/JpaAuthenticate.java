package wtchrs.SpringCommunity.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import wtchrs.SpringCommunity.entity.user.User;

import java.util.Collection;
import java.util.Collections;

public class JpaAuthenticate implements Authentication {

    private final User user;

    public JpaAuthenticate(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableList(user.getAuthorities());
    }

    @Override
    public Object getCredentials() {
        return user.getPassword();
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return user.getId();
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    }

    @Override
    public String getName() {
        return user.getUsername();
    }
}
