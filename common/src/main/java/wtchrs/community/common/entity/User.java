package wtchrs.community.common.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Entity(name = "user_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(length = 25, updatable = false, unique = true)
    private String username;
    private String password;

    private String name;

    @Column(length = 1000)
    private String description;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private final List<NameHistory> nameHistories = new LinkedList<>();

    public User(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public void editDescription(String description) {
        this.description = description;
    }

    public boolean checkPassword(String passwordInput) {
        return this.password.equals(passwordInput);
    }

    public void changePassword(String currentPasswd, String newPasswd, String confirmPasswd) {
        if (!newPasswd.equals(confirmPasswd)) {
            throw new IllegalArgumentException("Not matched new password and confirm password");
        }
        if (!checkPassword(currentPasswd)) throw new IllegalStateException("Wrong password");
        this.password = newPasswd;
    }

    public NameHistory changeName(String name) {
        NameHistory nameHistory = new NameHistory(this, this.name);
        this.nameHistories.add(nameHistory);
        this.name = name;
        return nameHistory;
    }
}
