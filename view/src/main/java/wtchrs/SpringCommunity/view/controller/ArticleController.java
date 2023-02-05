package wtchrs.SpringCommunity.view.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import wtchrs.SpringCommunity.common.entity.Article;
import wtchrs.SpringCommunity.common.service.ArticleService;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/recent-articles")
    public String recentArticleList(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, 20, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<Article> articles = articleService.getAllBoardsArticles(pageRequest);
        model.addAttribute("articles", articles);

        return "article/recentArticles";
    }

    @GetMapping("/boards/{boardId}/articles")
    public String boardArticleList(Model model, @PathVariable("boardId") Long boardId,
                                   @RequestParam(value = "page", defaultValue = "1") int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, 20, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<Article> articles = articleService.getBoardArticles(boardId, pageRequest);
        model.addAttribute("articles", articles);

        return "article/boardArticles";
    }
}
