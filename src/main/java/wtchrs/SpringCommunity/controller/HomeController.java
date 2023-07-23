package wtchrs.SpringCommunity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import wtchrs.SpringCommunity.service.ArticleService;
import wtchrs.SpringCommunity.service.BoardService;


@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BoardService boardService;

    private final ArticleService articleService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("boards", boardService.getBoards());
        PageRequest articlePageRequest = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "createdDate"));
        model.addAttribute("articles", articleService.getAllBoardsArticles(articlePageRequest));
        return "home";
    }
}
