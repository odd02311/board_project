package com.fastcampus.board_project.service;

import com.fastcampus.board_project.domain.Hashtag;
import com.fastcampus.board_project.repository.HashtagRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class HashtagService {
    private final HashtagRepository hashtagRepository;

    public HashtagService(HashtagRepository hashtagRepository) {
        this.hashtagRepository = hashtagRepository;
    }

    public Set<String> parseHashtagNames(String content) {
        if(content == null) {
            return Set.of();    // 불변 개체 of
        }

        Pattern pattern = Pattern.compile("#[\\w가-힣]+"); // 해시태그 정규표현식
        Matcher matcher = pattern.matcher(content.strip()); // 패턴을 만들었다면 matcher | .strip == 앞뒤 공백 문자가 있으면 자른다.
        Set<String> result = new HashSet<>();

        while(matcher.find()) {  // 얼마나 반복할지 모르니 무한루프 | .find() == 정규표현식에서(regular expression) 맞는 단어가 발견되면 stop
            result.add(matcher.group().replace("#", "")); // DB에는 #을 빼고 저장

        }
        return Set.copyOf(result);
    }

    public Set<Hashtag> findHashtagsByNames(Set<String> hashtagNames) {
        return new HashSet<>(hashtagRepository.findByHashtagNameIn(hashtagNames));
    }

    public void deleteHashtagWithoutArticles(Long hashtagId) {
        Hashtag hashtag = hashtagRepository.getReferenceById(hashtagId);
        if(hashtag.getArticles().isEmpty()){
            hashtagRepository.delete(hashtag);
        }
    }
    /*
     * 게시글이 없는 해시태그 delete
     * 글을 쓰면 자동으로 안에 있는 본문을 파싱해서 해시태그를 생성 및 DB 저장
     * 하지만 글이 삭제되면 글 안에 있던 해시태그들도 삭제를 하면 안된다.
     * 글에 있는 해시태그가 다른 글에서 쓰일 경우가 있기때문
     * 중복을 허용하지 않고 모두 하나의 해시태그로 일괄관리를 하고 있기 때문에
     * 한 글에서 등장했던 해시태그가 그 글이 지워졌다고 해서 바로 지워지는 일은 없어야 된다.
     * 모든 게시글이 지워졌을때 해시태그가 빠져야 한다.
     * 아무 게시글도 특정 해시태그를 가지고 있지 않을때 지워도 된다.
     * deleteHashtagWithoutArticles 로직
     */


}
