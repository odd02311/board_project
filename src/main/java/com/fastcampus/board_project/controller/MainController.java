package com.fastcampus.board_project.controller;

import com.fastcampus.board_project.dto.response.ArticleCommentResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Controller
public class MainController {

    @GetMapping("/")
    public String root() {
        return "forward:/articles";
    }

}
