package com.fastcampus.board_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HashTagsController {

    @GetMapping("/hashtags")
    public String showHashTags() {
        return "hashtags/hashtags";
    }
}
