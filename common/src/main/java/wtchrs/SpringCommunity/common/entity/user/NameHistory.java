package wtchrs.SpringCommunity.common.entity.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wtchrs.SpringCommunity.common.entity.BaseEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class NameHistory extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 25)
    private String history;

    public NameHistory(User user, String history) {
        this.user = user;
        this.history = history;
    }
}
