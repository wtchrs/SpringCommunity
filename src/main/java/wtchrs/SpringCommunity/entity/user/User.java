package wtchrs.SpringCommunity.entity.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import wtchrs.SpringCommunity.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "user_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity implements UserDetails {

    // TODO: add profile image.

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(length = 25, updatable = false, unique = true)
    private String username;

    private String password;

    private String name;

    @Lob
    private String description;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private final List<NameHistory> nameHistories = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private final List<Authority> authorities = new ArrayList<>();

    public User(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public void editDescription(String description) {
        this.description = description;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public NameHistory changeName(String name) {
        NameHistory nameHistory = new NameHistory(this, this.name);
        this.nameHistories.add(nameHistory);
        this.name = name;
        return nameHistory;
    }

    public void addAuthority(String authorityName) {
        Authority authority = new Authority(this, authorityName);
        this.authorities.add(authority);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
