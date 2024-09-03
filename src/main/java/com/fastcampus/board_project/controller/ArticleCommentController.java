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
    public String deleteArticleComment(@PathVariable Long commentId, Long articleId) {
        articleCommentService.deleteArticleComment(commentId);

        return "redirect:/articles/" + articleId;
    }
}
