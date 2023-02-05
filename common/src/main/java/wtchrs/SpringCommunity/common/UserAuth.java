package wtchrs.SpringCommunity.common;

import lombok.Getter;

@Getter
public class UserAuth {

    private final Long id;

    public UserAuth(Long id) {
        this.id = id;
    }
}
