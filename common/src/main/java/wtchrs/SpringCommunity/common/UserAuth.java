package wtchrs.SpringCommunity.common;

import lombok.Getter;

@Getter
public class UserAuth {

    private final Long id;
    private final String username;

    public UserAuth(Long id ,String username) {
        this.id = id;
        this.username = username;
    }
}
