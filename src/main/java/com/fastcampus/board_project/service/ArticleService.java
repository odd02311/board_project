package com.fastcampus.board_project.service;

import com.fastcampus.board_project.domain.Article;
import com.fastcampus.board_project.domain.UserAccount;
import com.fastcampus.board_project.domain.constant.SearchType;
import com.fastcampus.board_project.dto.ArticleDto;
import com.fastcampus.board_project.dto.ArticleWithCommentsDto;
import com.fastcampus.board_project.repository.ArticleRepository;
import com.fastcampus.board_project.repository.UserAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

        private final ArticleRepository articleRepository;
        private final UserAccountRepository userAccountRepository;


    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        if(searchKeyword == null || searchKeyword.isBlank()){
            return articleRepository.findAll(pageable).map(ArticleDto::from);
        }

        return switch(searchType){
                case TITLE -> articleRepository.findByTitleContaining(searchKeyword, pageable).map(ArticleDto::from);
                case CONTENT -> articleRepository.findByContentContaining(searchKeyword, pageable).map(ArticleDto::from);
                case ID -> articleRepository.findByUserAccount_UserIdContaining(searchKeyword, pageable).map(ArticleDto::from);
                case NICKNAME -> articleRepository.findByUserAccount_NicknameContaining(searchKeyword, pageable).map(ArticleDto::from);
            case HASHTAG -> articleRepository.findByHashtagNames(
                            Arrays.stream(searchKeyword.split(" ")).toList(),
                            pageable
                    )
                    .map(ArticleDto::from);

        };

    }

    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticleWithComments(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    @Transactional(readOnly = true)
    public ArticleDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
            .map(ArticleDto::from)
            .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    public void saveArticle(ArticleDto dto) {

        UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());
        articleRepository.save(dto.toEntity(userAccount));
    }

    public void updateArticle(Long articleId, ArticleDto dto) {
        try {
            Article article = articleRepository.getReferenceById(articleId);
            UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());

            if(article.getUserAccount().equals(userAccount)) { // 게시글 사용자와 인증된 사용자가 같은지 검증
                if (dto.title() != null) {
                    article.setTitle(dto.title());
                } // if는 null이 왔을때를 위한 방어코드
                if (dto.content() != null) {
                    article.setContent(dto.content());
                }
            }
        } catch(EntityNotFoundException e) {
            log.warn("게시글 수정 실패. 게시글을 수정하는데 필요한 정보를 찾을 수 없음. - {}", e.getLocalizedMessage());
        }

    }


    public void deleteArticle(long articleId, String userId) {
        articleRepository.deleteByIdAndUserAccount_UserId(articleId, userId);
    }

    public long getArticleCount() {
        return articleRepository.count();
    }

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticlesViaHashtag(String hashtag, Pageable pageable) {
        if (hashtag == null || hashtag.isBlank()) {
            return Page.empty(pageable);
        }

        return articleRepository.findByHashtagNames(null, pageable).map(ArticleDto::from);
    }

    public List<String> getHashtags() {
        return articleRepository.findAllDistinctHashtags();
    }
}
/**
 *  getReferenceById - 무조건 entity를 조회하는 쿼리를 준다.
 *  데이터가 필요하지않아도 select 쿼리문을 준다.
 *
 *  ex): Article article = articleRepository.findById(dto.id()); // 이 쿼리도 같이 반영됨. 개발자는 id를 이미 인지하고 있음
 *       article.setHashtag("#java");
 *       articleRepository.save(article); // 쿼리로 저장만 하고 싶은데
 *
 *       이럴때 쓸수 있는게 findById 대신 getReferenceById 이다. getone()과 동일함
 *
 */