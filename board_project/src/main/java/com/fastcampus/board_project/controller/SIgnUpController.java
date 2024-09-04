package com.fastcampus.board_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SIgnUpController {

    @GetMapping("/signup")
    public String showSignUpForm() {
        return "articles/signup";
    }
}
