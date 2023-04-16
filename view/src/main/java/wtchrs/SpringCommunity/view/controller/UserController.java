package wtchrs.SpringCommunity.view.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import wtchrs.SpringCommunity.common.UserAuth;
import wtchrs.SpringCommunity.common.request.SignInRequest;
import wtchrs.SpringCommunity.common.request.SignUpRequest;
import wtchrs.SpringCommunity.common.service.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/sign-in")
    public String signIn(@ModelAttribute("signInRequest") SignInRequest signInRequest) {
        return "signIn";
    }

    @PostMapping("/sign-in")
    public String signInProcess(HttpServletRequest request,
                                @Validated @ModelAttribute("signInRequest") SignInRequest signInRequest,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "signIn";
        UserAuth auth = userService.signIn(signInRequest.getUsername(), signInRequest.getPassword());
        if (auth == null) {
            bindingResult.reject("wrongIdPassword");
            return "signIn";
        }
        request.getSession().setAttribute("auth", auth);
        return "redirect:/";
    }

    @GetMapping("/sign-up")
    public String signUp(@ModelAttribute("signUpRequest") SignUpRequest signUpRequest) {
        return "signUp";
    }

    @PostMapping("/sign-up")
    public String signUpProcess(@Validated @ModelAttribute("signUpRequest") SignUpRequest signUpRequest,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "signUp";
        userService.signUp(signUpRequest.toUser());
        return "redirect:/sign-in";
    }

    @GetMapping("/sign-out")
    public String signOut(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();
        return "redirect:/";
    }
}
