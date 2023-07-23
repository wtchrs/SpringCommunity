package wtchrs.SpringCommunity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wtchrs.SpringCommunity.auth.JpaAuthenticate;
import wtchrs.SpringCommunity.controller.request.BoardCreateRequest;
import wtchrs.SpringCommunity.entity.user.User;
import wtchrs.SpringCommunity.service.BoardService;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boards")
    public String boardList(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        model.addAttribute("boards", boardService.getBoardsWithUser(PageRequest.of(page - 1, 20)));
        return "board/boards";
    }

    @GetMapping("/boards/create")
    public String createBoard(@ModelAttribute("boardCreate") BoardCreateRequest request) {
        return "board/createBoard";
    }

    @PostMapping("/boards/create")
    public String createBoardProcess(
            RedirectAttributes redirect,
            @ModelAttribute("boardCreate") BoardCreateRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "board/createBoard";
        }
        JpaAuthenticate auth = (JpaAuthenticate) SecurityContextHolder.getContext().getAuthentication();
        User user = auth.getUser();
        Long id = boardService.createBoard(user.getId(), request.getName(), request.getDescription());
        redirect.addAttribute("boardId", id);
        return "redirect:/boards/{boardId}/articles";
    }

}
