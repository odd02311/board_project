package com.fastcampus.board_project.controller;

import com.fastcampus.board_project.dto.UserAccountDto;
import com.fastcampus.board_project.dto.request.ArticleCommentRequest;
import com.fastcampus.board_project.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;


    @PostMapping("/new")
    // TODO: 인증 정보를 넣어야 한다.
    public String postNewArticleComment(ArticleCommentRequest articleCommentRequest) {
        articleCommentService.saveArticleComment(articleCommentRequest.toDto(UserAccountDto.of(
                "author1", "password", "author1@mail.com", "AUTHOR1", null

        )));


        return "redirect:/articles/" + articleCommentRequest.articleId();
    }

    @PostMapping("/{commentId}/delete") // form태그는 get/post만 지원해서 delete도 post로 설정
    public String deleteArticleComment(@PathVariable Long commentId, @RequestParam Long articleId) {
        articleCommentService.deleteArticleComment(commentId);

        return "redirect:/articles/" + articleId;
    }

    @PostMapping("/{commentId}/update")
    public String updateComment(@PathVariable Long commentId, @RequestParam Long articleId, @RequestParam String content) {

        System.out.println("Debug: commentId = " + commentId);
        System.out.println("Debug: articleId = " + articleId);
        System.out.println("Debug: content = " + content);

        articleCommentService.updateArticleComment(commentId, content);
        return "redirect:/articles/" + articleId;
    }
}
