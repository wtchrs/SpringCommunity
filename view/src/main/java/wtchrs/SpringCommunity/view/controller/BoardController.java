package wtchrs.SpringCommunity.view.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wtchrs.SpringCommunity.common.service.BoardService;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boards")
    public String boardList(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        model.addAttribute("boards", boardService.getBoardsWithUser(PageRequest.of(page - 1, 20)));
        return "board/boards";
    }
}
