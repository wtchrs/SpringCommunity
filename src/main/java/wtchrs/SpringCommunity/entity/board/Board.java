package wtchrs.SpringCommunity.entity.board;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wtchrs.SpringCommunity.entity.BaseEntity;
import wtchrs.SpringCommunity.entity.user.User;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Board extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @Column(name = "board_name", unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    private User creator;

    @Lob
    private String description;

    public Board(String name, User creator, String description) {
        this.name = name;
        this.creator = creator;
        this.description = description;
    }

}
