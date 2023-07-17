package wtchrs.SpringCommunity.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wtchrs.SpringCommunity.auth.JpaAuthenticate;
import wtchrs.SpringCommunity.entity.article.Article;
import wtchrs.SpringCommunity.entity.board.Board;
import wtchrs.SpringCommunity.entity.board.BoardRepository;
import wtchrs.SpringCommunity.entity.user.User;
import wtchrs.SpringCommunity.entity.user.UserRepository;
import wtchrs.SpringCommunity.request.ArticleWriteRequest;
import wtchrs.SpringCommunity.service.ArticleService;
import wtchrs.SpringCommunity.service.ImageStoreService;

import java.util.ArrayList;
import java.util.List;

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

        Board findBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("board", findBoard);

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
            Model model, @PathVariable Long boardId, @ModelAttribute("article") ArticleWriteRequest article,
            @RequestParam(required = false) List<MultipartFile> images) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalStateException("Not Exists board"));
        model.addAttribute("board", board);
        if (images == null) model.addAttribute("images", new ArrayList<MultipartFile>());
        return "article/postArticle";
    }

    @PostMapping("/boards/{boardId}/articles/write")
    public String postArticleProcess(
            Model model, RedirectAttributes redirect, @PathVariable Long boardId,
            @ModelAttribute("article") ArticleWriteRequest article,
            @RequestParam(required = false) List<MultipartFile> images, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Board board = boardRepository.findById(boardId)
                    .orElseThrow(() -> new IllegalStateException("Not exists board"));
            model.addAttribute("board", board);
            return "article/postArticle";
        }

        JpaAuthenticate auth = (JpaAuthenticate) SecurityContextHolder.getContext().getAuthentication();
        User user = auth.getUser();
        Long articleId = articleService.post(user.getId(), boardId, article.getTitle(), article.getContent(), images);

        redirect.addAttribute("articleId", articleId);
        return "redirect:/boards/{boardId}/articles/{articleId}";
    }

    @GetMapping("/boards/{boardId}/articles/{articleId}/delete")
    public String deleteArticle(RedirectAttributes redirect, @PathVariable Long boardId, @PathVariable Long articleId) {
        JpaAuthenticate auth = (JpaAuthenticate) SecurityContextHolder.getContext().getAuthentication();
        User user = auth.getUser();
        articleService.deleteArticle(articleId, user);
        return "redirect:/boards/{boardId}/articles?deleted";
    }

}
