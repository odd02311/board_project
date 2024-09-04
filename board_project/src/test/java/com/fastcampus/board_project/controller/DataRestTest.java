package com.fastcampus.board_project.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled("Spring Data REST 통합테스트 비활성화")
//@WebMvcTest // controller 외의 bean들을 로드하지 않음
@DisplayName("Data REST - API 테스트")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class DataRestTest {


  private final MockMvc mvc;

  public DataRestTest(@Autowired MockMvc mvc) {
    this.mvc = mvc;
  }

  @DisplayName("[api] 게시글 리스트 조회")
  @Test
  void givenNothing_whenRequestingArticles_thenReturnsArticlesJsonResponse() throws Exception { // endpoint가 존재하는지만 보는 테스트
    // Given

    // When & Then
    mvc.perform(get("/api/articles"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
//        .andDo(print());

  }
  
  @DisplayName("[api] 게시글 단일 조회")
  @Test
  void givenNothing_whenRequestingArticle_thenReturnsArticleJsonResponse() throws Exception {
    // Given

    // When & Then
    mvc.perform(get("/api/articles/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
//        .andDo(print());

  }

  @DisplayName("[api] 게시글 -> 댓글 리스트 조회")
  @Test
  void givenNothing_whenRequestingArticleCommentsFromArticle_thenReturnsArticleCommentsJsonResponse() throws Exception {
    // Given

    // When & Then
    mvc.perform(get("/api/articles/1/articleComments"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
//        .andDo(print());

  }


  @DisplayName("[api] 댓글 리스트 조회")
  @Test
  void givenNothing_whenRequestingArticleComments_thenReturnsArticleCommentsJsonResponse() throws Exception {
    // Given

    // When & Then
    mvc.perform(get("/api/articleComments"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
//        .andDo(print());

  }

  @DisplayName("[api] 댓글 리스트 조회")
  @Test
  void givenNothing_whenRequestingArticleComment_thenReturnsArticleCommentJsonResponse() throws Exception {
    // Given

    // When & Then
    mvc.perform(get("/api/articleComments/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
//        .andDo(print());

  }


}
