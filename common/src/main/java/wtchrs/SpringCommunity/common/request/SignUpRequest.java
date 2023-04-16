package wtchrs.SpringCommunity.common.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wtchrs.SpringCommunity.common.entity.user.User;

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @NotBlank
    private String confirm;

    public SignUpRequest(String username, String name, String password, String confirm) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.confirm = confirm;
    }

    public boolean confirmPassword() {
        return password.equals(confirm);
    }

    public User toUser() {
        if (!password.equals(confirm)) {
            throw new IllegalStateException("Not matched password and confirm password");
        }
        return new User(username, password, name);
    }
}
