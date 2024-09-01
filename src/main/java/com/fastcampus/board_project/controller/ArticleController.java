package com.fastcampus.board_project.controller;

import com.fastcampus.board_project.domain.type.SearchType;
import com.fastcampus.board_project.dto.response.ArticleResponse;
import com.fastcampus.board_project.dto.response.ArticleWithCommentsResponse;
import com.fastcampus.board_project.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public String articles(
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable, // 정렬 생성자 기준 내림차순
            ModelMap map
    ){
      map.addAttribute("articles", articleService.searchArticles(searchType, searchValue, pageable)
              .map(ArticleResponse::from));

      return "articles/index";
    }

    @GetMapping("/{articleId}")
    public String article(@PathVariable Long articleId, ModelMap map){
      ArticleWithCommentsResponse article =   ArticleWithCommentsResponse.from(articleService.getArticle(articleId));
      map.addAttribute("article", article); // (완료)TODO: 구현할 때 여기에 실제 데이터 넣어야 함
      map.addAttribute("articleComments", article.articleCommentResponse());

      return "articles/detail";
    }

}
