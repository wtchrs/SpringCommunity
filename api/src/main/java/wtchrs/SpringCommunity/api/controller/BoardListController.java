package wtchrs.SpringCommunity.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wtchrs.SpringCommunity.common.service.BoardService;
import wtchrs.SpringCommunity.common.entity.board.Board;
import wtchrs.SpringCommunity.common.response.BoardResponse;

@RestController
@RequiredArgsConstructor
public class BoardListController {

    private final BoardService boardService;

    @GetMapping("/boards")
    public ResponseEntity<Page<BoardResponse>> boardList(@Param(value = "page") int page) {
        Pageable pageable = PageRequest.of(page - 1, 20);
        Page<Board> boards = boardService.getBoards(pageable);
        return ResponseEntity.ok(boards.map(BoardResponse::of));
    }
}
