package wtchrs.SpringCommunity.controller.request;

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

}
