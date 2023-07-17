package wtchrs.SpringCommunity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import wtchrs.SpringCommunity.request.SignInRequest;
import wtchrs.SpringCommunity.request.SignUpRequest;
import wtchrs.SpringCommunity.service.JpaUserDetailsManager;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final JpaUserDetailsManager userDetailsManager;

    @GetMapping("/sign-in")
    public String signIn(@ModelAttribute("signInRequest") SignInRequest signInRequest) {
        return "signIn";
    }

    @GetMapping("/sign-up")
    public String signUp(@ModelAttribute("signUpRequest") SignUpRequest signUpRequest) {
        return "signUp";
    }

    @PostMapping("/sign-up")
    public String signUpProcess(
            @Validated @ModelAttribute("signUpRequest") SignUpRequest signUpRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) return "signUp";
        userDetailsManager.createUser(signUpRequest.toUser());
        return "redirect:/sign-in";
    }

}
