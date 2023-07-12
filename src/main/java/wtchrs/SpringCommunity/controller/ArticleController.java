package wtchrs.SpringCommunity.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wtchrs.SpringCommunity.auth.UserAuth;
import wtchrs.SpringCommunity.entity.article.Article;
import wtchrs.SpringCommunity.entity.board.Board;
import wtchrs.SpringCommunity.entity.board.BoardRepository;
import wtchrs.SpringCommunity.entity.user.UserRepository;
import wtchrs.SpringCommunity.request.ArticleWriteRequest;
import wtchrs.SpringCommunity.service.ArticleService;
import wtchrs.SpringCommunity.service.ImageStoreService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final ImageStoreService imageStoreService;

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @GetMapping("/recent-articles")
    public String recentArticleList(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, 20, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<Article> articles = articleService.getAllBoardsArticles(pageRequest);
        model.addAttribute("articles", articles);

        return "article/recentArticles";
    }

    @GetMapping("/boards/{boardId}/articles")
    public ModelAndView boardArticleList(
            Model model, @PathVariable("boardId") Long boardId,
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

    @GetMapping("/boards/{boardId}/articles/{articleId}")
    public String articleDetail(Model model, @PathVariable Long boardId, @PathVariable Long articleId) {
        Long findBoardId = articleService.getBoardIdFromArticle(articleId);
        if (!findBoardId.equals(boardId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        Article article = articleService.viewContent(articleId);
        model.addAttribute("article", article);

        List<String> imageUrls = article.getImages().stream().map(imageStoreService::getResizedImageUrl).toList();
        model.addAttribute("imageUrls", imageUrls);

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<Article> articles = articleService.getBoardArticles(boardId, pageRequest);
        model.addAttribute("articles", articles);

        return "article/articleDetail";
    }

    @GetMapping("/boards/{boardId}/articles/write")
    public String postArticleForm(
            HttpServletRequest request, Model model, RedirectAttributes redirect, @PathVariable Long boardId,
            @ModelAttribute("article") ArticleWriteRequest article) {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("auth") == null) {
            redirect.addAttribute("redirectUrl", request.getRequestURI());
            return "redirect:/sign-in";
        }

        Optional<Board> findBoard = boardRepository.findById(boardId);
        if (findBoard.isEmpty()) throw new IllegalStateException("Not exists board");
        Board board = findBoard.get();
        model.addAttribute("board", board);

        return "article/postArticle";
    }

    @PostMapping("/boards/{boardId}/articles/write")
    public String postArticleProcess(
            HttpServletRequest request, Model model, RedirectAttributes redirect, @PathVariable Long boardId,
            @ModelAttribute("article") ArticleWriteRequest article, BindingResult bindingResult) {

        HttpSession session = request.getSession(false);
        UserAuth auth;
        if (session == null || (auth = (UserAuth) session.getAttribute("auth")) == null) {
            redirect.addAttribute("redirectUri", request.getRequestURI());
            return "redirect:/sign-in";
        }

        if (bindingResult.hasErrors()) {
            Optional<Board> findBoard = boardRepository.findById(boardId);
            if (findBoard.isEmpty()) throw new IllegalStateException("Not exists board");
            Board board = findBoard.get();
            model.addAttribute("board", board);
            return "article/postArticle";
        }

        Long articleId = articleService.post(auth.getId(), boardId, article.getTitle(), article.getContent(),
                article.getImages());

        redirect.addAttribute("boardId", boardId);
        redirect.addAttribute("articleId", articleId);
        return "redirect:/boards/{boardId}/articles/{articleId}";
    }

    @PostMapping("/boards/{boardId}/articles/{articleId}/delete")
    public String deleteArticle(HttpServletRequest request, @PathVariable Long boardId, @PathVariable Long articleId) {
        // TODO
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new UnsupportedOperationException();
        }
        throw new UnsupportedOperationException();
    }
}
