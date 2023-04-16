package wtchrs.SpringCommunity.view.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import wtchrs.SpringCommunity.common.entity.article.Article;
import wtchrs.SpringCommunity.common.entity.board.Board;
import wtchrs.SpringCommunity.common.entity.board.BoardRepository;
import wtchrs.SpringCommunity.common.service.ArticleService;
import wtchrs.SpringCommunity.common.service.BoardService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final BoardService boardService;

    private final BoardRepository boardRepository;

    @GetMapping("/recent-articles")
    public String recentArticleList(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, 20, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<Article> articles = articleService.getAllBoardsArticles(pageRequest);
        model.addAttribute("articles", articles);

        return "article/recentArticles";
    }

    @GetMapping("/boards/{boardId}/articles")
    public ModelAndView boardArticleList(Model model,
                                         @PathVariable("boardId") Long boardId,
                                         @RequestParam(value = "page", defaultValue = "1") int page) {
        Optional<Board> findBoard = boardRepository.findById(boardId);
        if (findBoard.isEmpty()) {
            ModelAndView modelAndView = new ModelAndView("error/404");
            modelAndView.setStatus(HttpStatusCode.valueOf(404));
            return modelAndView;
        }
        model.addAttribute("board", findBoard.get());

        PageRequest pageRequest = PageRequest.of(page - 1, 20, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<Article> articles = articleService.getBoardArticles(boardId, pageRequest);
        model.addAttribute("articles", articles);

        return new ModelAndView("article/boardArticles");
    }

    @GetMapping("/boards/{boardId}/articles/write")
    public String writeForm(@PathVariable Long boardId) {
        // TODO: write 'templates/article/write.html'
        // TODO: write form object class
        return "article/write";
    }

    @PostMapping("/boards/{boardId}/articles/write")
    public String writeProcess(@PathVariable Long boardId) {
        // TODO
        throw new UnsupportedOperationException("Not implemented");
    }

    @GetMapping("/boards/{boardId}/articles/{articleId}")
    public String articleDetail(Model model, @PathVariable Long boardId, @PathVariable Long articleId) {
        Long findBoardId = articleService.getBoardIdFromArticle(articleId);
        if (!findBoardId.equals(boardId)) return "redirect:/boards/" + findBoardId + "/articles/" + articleId;
        Article article = articleService.viewContent(articleId);
        model.addAttribute("article", article);

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<Article> articles = articleService.getBoardArticles(boardId, pageRequest);
        model.addAttribute("articles", articles);

        return "article/articleDetail";
    }
}
