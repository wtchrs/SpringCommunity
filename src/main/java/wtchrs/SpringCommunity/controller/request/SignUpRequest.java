package wtchrs.SpringCommunity.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wtchrs.SpringCommunity.entity.user.User;

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
