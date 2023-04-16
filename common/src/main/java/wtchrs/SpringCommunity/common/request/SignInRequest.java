package wtchrs.SpringCommunity.common.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignInRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public SignInRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
