package com.fastcampus.board_project.controller;

import com.fastcampus.board_project.domain.constant.FormStatus;
import com.fastcampus.board_project.domain.constant.SearchType;
import com.fastcampus.board_project.dto.UserAccountDto;
import com.fastcampus.board_project.dto.request.ArticleRequest;
import com.fastcampus.board_project.dto.response.ArticleResponse;
import com.fastcampus.board_project.dto.response.ArticleWithCommentsResponse;
import com.fastcampus.board_project.dto.security.BoardPrincipal;
import com.fastcampus.board_project.service.ArticleService;
import com.fastcampus.board_project.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final PaginationService paginationService;

    @GetMapping
    public String articles(
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable, // 정렬 생성자 기준 내림차순
            ModelMap map
    ){

      SearchType type = searchType != null ? SearchType.fromString(searchType) : null;


      Page<ArticleResponse> articles = articleService.searchArticles(type, searchValue, pageable).map(ArticleResponse::from);
        List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());

        map.addAttribute("articles", articles);
        map.addAttribute("paginationBarNumbers", barNumbers);
        map.addAttribute("searchTypes", SearchType.values());

      return "articles/postList";
    }

    @GetMapping("/{articleId}")
    public String article(@PathVariable Long articleId, ModelMap map){
      ArticleWithCommentsResponse article = ArticleWithCommentsResponse.from(articleService.getArticleWithComments(articleId));

      map.addAttribute("article", article); // (완료)TODO: 구현할 때 여기에 실제 데이터 넣어야 함
      map.addAttribute("articleComments", article.articleCommentResponse());
      map.addAttribute("totalCount", articleService.getArticleCount());

      return "articles/postDetail";
    }

  @GetMapping("/search-hashtag")
  public String searchHashtag(
      @RequestParam(required = false) String searchValue,
      @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
      ModelMap map
  ) {
    Page<ArticleResponse> articles = articleService.searchArticlesViaHashtag(searchValue, pageable).map(ArticleResponse::from);
    List<Integer> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());
    List<String> hashtags = articleService.getHashtags();

    map.addAttribute("articles", articles);
    map.addAttribute("hashtags", hashtags);
    map.addAttribute("paginationBarNumbers", barNumbers);
    map.addAttribute("searchType", SearchType.HASHTAG);

    return "articles/search-hashtag";
  }

  @GetMapping("/form")
  public String articleForm(ModelMap map) {
    map.addAttribute("formStatus", FormStatus.CREATE);

    return "articles/postForm";
  }

  @PostMapping ("/form")
  public String postNewArticle(
          ArticleRequest articleRequest,
          @AuthenticationPrincipal BoardPrincipal boardPrincipal
  ) {
    // TODO: 인증 정보를 넣어줘야 한다. (완료)
    articleService.saveArticle(articleRequest.toDto(boardPrincipal.toDto()));

    return "redirect:/articles";
  }

  @GetMapping("/{articleId}/form")
  public String updateArticleForm(@PathVariable Long articleId, ModelMap map) {
    ArticleResponse article = ArticleResponse.from(articleService.getArticle(articleId));

    map.addAttribute("article", article);
    map.addAttribute("formStatus", FormStatus.UPDATE);

    return "articles/form";
  }

  @PostMapping ("/{articleId}/form")
  public String updateArticle(
          @PathVariable Long articleId, ArticleRequest articleRequest,
          @AuthenticationPrincipal BoardPrincipal boardPrincipal
  ) {
    // TODO: 인증 정보를 넣어줘야 한다. (완료)
    articleService.updateArticle(articleId, articleRequest.toDto(boardPrincipal.toDto()));

    return "redirect:/articles/" + articleId;
  }

  @PostMapping("/{articleId}/delete")
  public String deleteArticle(
          @PathVariable Long articleId,
          @AuthenticationPrincipal BoardPrincipal boardPrincipal
  ) {
    articleService.deleteArticle(articleId, boardPrincipal.getUsername());

    return "redirect:/articles";
  }

}
