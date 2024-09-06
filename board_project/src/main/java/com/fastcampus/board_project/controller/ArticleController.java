package com.fastcampus.board_project.controller;

import com.fastcampus.board_project.domain.constant.FormStatus;
import com.fastcampus.board_project.domain.constant.SearchType;
import com.fastcampus.board_project.dto.UserAccountDto;
import com.fastcampus.board_project.dto.request.ArticleRequest;
import com.fastcampus.board_project.dto.response.ArticleResponse;
import com.fastcampus.board_project.dto.response.ArticleWithCommentsResponse;
import com.fastcampus.board_project.service.ArticleService;
import com.fastcampus.board_project.service.PaginationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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
            @RequestParam(required = false) String newSearchType,
            @RequestParam(required = false) String newSearchValue,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable, // 정렬 생성자 기준 내림차순
            ModelMap map
    ) {

        log.info("searchType: {}", searchType);
        log.info("searchValue: {}", searchValue);
        log.info("newSearchType: {}", newSearchType);
        log.info("newSearchValue: {}", newSearchValue);

        SearchType type = (searchType != null && !searchType.isEmpty()) ? SearchType.fromString(searchType) : null;

        Page<ArticleResponse> articles;
        List<Integer> barNumbers;
        List<String> hashtags;

        if (type == SearchType.HASHTAG && searchValue != null) {
//            articles = articleService.searchArticlesViaHashtag(searchValue, pageable).map(ArticleResponse::from);
            articles = articleService.searchArticlesViaHashtagWithAdditionalFilter(searchValue, newSearchType, newSearchValue, pageable).map(ArticleResponse::from);
            barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());
            hashtags = articleService.getHashtags();

            map.addAttribute("articles", articles);
            map.addAttribute("hashtags", hashtags);
            map.addAttribute("paginationBarNumbers", barNumbers);
            map.addAttribute("searchType", SearchType.HASHTAG.name());
            map.addAttribute("searchValue", searchValue);

        } else {
            articles = articleService.searchArticles(type, searchValue, pageable).map(ArticleResponse::from);
            barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());
            map.addAttribute("articles", articles);
            map.addAttribute("paginationBarNumbers", barNumbers);
            map.addAttribute("searchTypes", SearchType.values());
            map.addAttribute("searchValue", searchValue);
        }

        return "articles/postList";
    }

    @GetMapping("/{articleId}")
    public String article(@PathVariable Long articleId, ModelMap map) {
        ArticleWithCommentsResponse article = ArticleWithCommentsResponse.from(articleService.getArticleWithComments(articleId));

        map.addAttribute("article", article); // (완료)TODO: 구현할 때 여기에 실제 데이터 넣어야 함
        map.addAttribute("articleComments", article.articleCommentResponse());

        return "articles/postDetail";
    }

    /*
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

        return "redirect:/articles";
    }

     */

    @GetMapping("/form")
    public String articleForm(ModelMap map) {
        map.addAttribute("formStatus", FormStatus.CREATE);

        return "articles/postForm";
    }

    @PostMapping("/form")
    public String postNewArticle(ArticleRequest articleRequest) {
        // TODO: 인증 정보를 넣어줘야 한다.
        articleService.saveArticle(articleRequest.toDto(UserAccountDto.of(
                "author1", "asdf1234", "author1@mail.com", "AUTHOR1", "memo"
        )));
        return "redirect:/articles";
    }

    @GetMapping("/{articleId}/form")
    public String updateArticleForm(@PathVariable Long articleId, ModelMap map) {
        ArticleResponse article = ArticleResponse.from(articleService.getArticle(articleId));

        map.addAttribute("article", article);
        map.addAttribute("formStatus", FormStatus.UPDATE);

        return "articles/postEdit";
    }

//  @PostMapping ("/{articleId}/form")
//  public String updateArticle(@PathVariable Long articleId, ArticleRequest articleRequest) {
//    // TODO: 인증 정보를 넣어줘야 한다.
//    articleService.updateArticle(articleId, articleRequest.toDto(UserAccountDto.of(
//        "author1", "asdf1234", "author1@mail.com", "AUTHOR1", "memo"
//        )));
//
//    return "redirect:/articles/" + articleId;
//  }

    @PostMapping("/{articleId}/form")
    public String updateArticle(
            @PathVariable Long articleId,
            ArticleRequest articleRequest
    ) {
        // 현재 인증 정보를 사용하는 부분은 생략하고 사용자 정보를 UserAccountDto로 전달
        articleService.updateArticle(articleId, articleRequest);
        return "redirect:/articles/" + articleId;
    }

    @PostMapping("/{articleId}/delete")
    public String deleteArticle(@PathVariable Long articleId) {
        // TODO: 인증 정보를 넣어줘야 한다.
        articleService.deleteArticle(articleId);

        return "redirect:/articles";
    }

}
