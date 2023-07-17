package wtchrs.SpringCommunity.entity.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue
    @Column(name = "authority_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String name;

    public Authority(User user, String name) {
        this.user = user;
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }

}
