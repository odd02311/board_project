package com.fastcampus.board_project.controller;

import com.fastcampus.board_project.dto.UserAccountDto;
import com.fastcampus.board_project.dto.UserAccountSignUpDto;
import com.fastcampus.board_project.dto.request.UserAccountRequest;
import com.fastcampus.board_project.dto.request.UserAccountSignUpRequest;
import com.fastcampus.board_project.service.UserAccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;
//    private final HttpServletRequest httpServletRequest;



    //  @PostMapping("/login")
//  public String login(
//      @ModelAttribute UserAccountRequest userAccountRequest,
//      HttpSession session,
//      Model model
//  ) {
//    System.out.println("userId: " + userAccountRequest.userId());
//    System.out.println("password: " + userAccountRequest.password());
//
//    UserAccountDto authenticatedUser = userAccountService.authenticate(
//        userAccountRequest.userId(),
//        userAccountRequest.password()
//    );
//
//    if (authenticatedUser != null) {
//      session.setAttribute("loggedInUser", authenticatedUser);
//      return "redirect:/";  // 로그인 성공 시 메인 페이지로 리다이렉트
//    } else {
//      model.addAttribute("loginError", "Invalid username or password");
//      return "index";  // 메인 페이지로 리다이렉트, 팝업에서 에러 메시지 처리
//    }
//  }

//    스프링 Security에서 처리했기 때문에 불필요
//    @PostMapping("/login")
//    public String login(
//            @ModelAttribute UserAccountRequest userAccountRequest,
//            BindingResult bindingResult,
//            HttpServletRequest httpServletRequest
//    ){
//        System.out.println(userAccountRequest.userId());
//
//        UserAccountDto authenticatedUserAccount = userAccountService.authenticate(
//                userAccountRequest.userId(),
//                userAccountRequest.password()
//        );
//        if(authenticatedUserAccount == null) {
//            bindingResult.reject("loginFail");
//        }
//
//        if(bindingResult.hasErrors()){
//            return "redirect:/";
//        }
//
//
//        HttpSession session = request.getSession(true);
//
//
//        httpServletRequest.getSession().invalidate();
//        HttpSession session = httpServletRequest.getSession(true);
//
//
//
//        session.setAttribute("userId", userAccountRequest.userId());
//        System.out.println("userId >>> " + session.getAttribute("userId"));
//        session.setMaxInactiveInterval(1800);
//
//        return "redirect:/";
//    }

//
//    @GetMapping("/")
//    public String home(HttpServletRequest httpServletRequest, Model model) {
//        HttpSession session = httpServletRequest.getSession(false);  // 세션이 있으면 가져오기
//        if (session != null && session.getAttribute("userId") != null) {
//            String userId = (String) session.getAttribute("userId");
//            model.addAttribute("userId", userId);  // 세션의 userId를 모델에 추가
//        } else {
//            model.addAttribute("userId", null);  // 세션이 없으면 userId를 null로 설정
//        }
//        return "redirect:articles/index";  // 메인 페이지로 이동
//    }
//

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("userAccountSignUpRequest", UserAccountSignUpRequest.of("", "", "", ""));
        return "/user/signup";
    }

    @PostMapping("/signup")
    public String signup(
            @ModelAttribute UserAccountSignUpRequest userAccountSignUpRequest,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "/user/signup";
        }

        try {
            UserAccountDto newUser = userAccountService.signUp(
                    UserAccountSignUpDto.of(
                            userAccountSignUpRequest.userId(),
                            userAccountSignUpRequest.userPassword(),
                            userAccountSignUpRequest.email(),
                            userAccountSignUpRequest.memo() // Occupation 값 전달
                    )
            );
        } catch (IllegalArgumentException e) {
            bindingResult.reject("signupFail", e.getMessage());
            return "/user/signup";
        }

        return "redirect:/";
    }


    @PostMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false); // 현재 세션을 가져옴
//      if (session != null) {
//          session.invalidate(); // 세션 무효화
//      }
        return "redirect:/";
    }

}