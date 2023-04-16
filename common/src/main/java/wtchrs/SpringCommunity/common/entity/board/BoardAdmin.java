package wtchrs.SpringCommunity.common.entity.board;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wtchrs.SpringCommunity.common.entity.BaseEntity;
import wtchrs.SpringCommunity.common.entity.user.User;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BoardAdmin extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "board_admin_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", updatable = false)
    private Board board;

    private boolean accepted;

    public BoardAdmin(User user, Board board) {
        this.user = user;
        this.board = board;
    }

    public void accept() {
        this.accepted = true;
    }
}
