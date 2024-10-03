package com.fastcampus.board_project.service;

import com.fastcampus.board_project.domain.Article;
import com.fastcampus.board_project.domain.Hashtag;
import com.fastcampus.board_project.domain.UserAccount;
import com.fastcampus.board_project.domain.constant.SearchType;
import com.fastcampus.board_project.dto.ArticleDto;
import com.fastcampus.board_project.dto.ArticleWithCommentsDto;
import com.fastcampus.board_project.repository.ArticleRepository;
import com.fastcampus.board_project.repository.HashtagRepository;
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
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

        private final HashtagService hashtagService;
        private final ArticleRepository articleRepository;
        private final UserAccountRepository userAccountRepository;
        private final HashtagRepository hashtagRepository;

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
        Set<Hashtag> hashtags = renewHashtagsFromContent(dto.content());

        Article article = dto.toEntity(userAccount);    // dto => entity 변환
        article.addHashtags(hashtags);  // entity로 변환 완료 되었다면 도메인에서 만들었던 별도의 메소드(addHashtags)
        articleRepository.save(article); // repository에서 저장

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



                /*
                  업데이트 로직
                    기존의 게시글에서 해시태그를 가져온다. | Set<Long> hashtagId = article.getHashtags()
                    가져온 해시태그로 article에 있는 clear 해시태그 (다 지운 후)
                    그 내용을 flush를 한다. (일단 delete 쿼리를 발생 시켜, 해당하는 해시태그를 전부 지운다.)
                    delete 해주지 않으면 밑에서 새로 해시태그를 추가하는 부분이랑 지우는 부분이 중복이 일어나서
                    delete 수행이 원하는 대로 되지 않을 수 있다. 그래서 여기서 지우는 내용을 먼저 실제로 확정을 짓기 위해 flush를 한다.

                    가져온 해시태그 id로부터 더이상 게시글이 존재하지 않는다면 
                 */

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

    private Set<Hashtag> renewHashtagsFromContent(String content) {
        Set<String> hashtagNamesInContent = hashtagService.parseHashtagNames(content);
        Set<Hashtag> hashtags = hashtagService.findHashtagsByNames(hashtagNamesInContent);
        Set<String> existingHashtagNames = hashtags.stream()
                .map(Hashtag::getHashtagName)
                .collect(Collectors.toUnmodifiableSet());

        hashtagNamesInContent.forEach(newHashtagName -> {
            if (!existingHashtagNames.contains(newHashtagName)) {
                hashtags.add(Hashtag.of(newHashtagName));
            }
        });

        return hashtags;

        /*
            로직의 흐름:
            String content로 본문을 매개변수로 받는다.
            본문 안에 있는 새로운 parsing 해야할 새로운 해시태그의 이름들을 뽑아낸다. hashtagService.parseHashtagNames(content)
            parsing한 곃과를 이제 Set<String>으로 받는다. | hashtagNamesInContent
            hashtag 서비스 단에서 findHashtagsByNames(hashtagNamesInContent)로 db에서 검색 | hashtags = 실제로 존재하는 해시태그
            entity를 그냥 쓸 것은 아니기 때문에 existingHashtagNames 를 stream.map으로
            해시태그 name을 뽑아서 collect set으로 해시태그를 만든다.
            2개의 Set이 존재한다: 본문에서 parse한 해시태그, 실제 db에 존재하는 해시태그

            본문에서 parse한 set으로 forEach를 돌려서 db에 새로운 해시태그가 존재하는가 검사
            그리고 add(Hashtag.of(newHashtagName))으로 db에 저장

            그리고 저장된 값을 리턴하면 된다.
         */
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