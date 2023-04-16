package wtchrs.SpringCommunity.common.response;

import lombok.Getter;
import wtchrs.SpringCommunity.common.entity.board.Board;

import java.time.LocalDateTime;

@Getter
public class BoardResponse {

    private final Long id;
    private final String name;
    private final String creatorName;

    private final LocalDateTime createdDate;
    private final LocalDateTime lastModifiedDate;

    public BoardResponse(Long id, String name, String creatorName, LocalDateTime createdDate,
                         LocalDateTime lastModifiedDate) {
        this.id = id;
        this.name = name;
        this.creatorName = creatorName;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public static BoardResponse of(Board board) {
        return new BoardResponse(board.getId(), board.getName(), board.getCreator().getName(), board.getCreatedDate(),
                                 board.getLastModifiedDate());
    }
}
