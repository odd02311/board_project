package com.fastcampus.board_project.config;

import com.fastcampus.board_project.dto.security.BoardPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {
    @Bean   // 아직 인증 기능이 구현되지않았기 때문에 생상자에 임의로 이름을 넣어주는 환경설정
    public AuditorAware<String> auditorAware() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(BoardPrincipal.class::cast)
                .map(BoardPrincipal::getUsername); // TODO: SPRING SECURITY로 인증 기능을 붙이게 될때, 수정

    }
}

/*
    SecurityContextHolder는 springsecurity를 사용한다면 secuirty에 대한 정보를 모두 가지고있는 class 이다
    SecurityContextHolder에서부터 getContext를 이용해 SecurityContext를 불러온다.
    SecurityContext에는 authentication 정보가 있고
    .filter를 이용해 isAuthenticated로 인증 되있는지 확인한 다음에
    그로부터 로그인 정보인 principal을 getPrincipal로 꺼내온다.
    principal과 관련된 정보를 따로 record로 BoardPrincipal로 구현체를 만들었다.
    이때 상속하기 위해 UserDetails 인터페이스를 사용
    UserDetails로 만든 정보를 받아와서 인증정보를 담아준다.
    이때문에 BoardPrincipal을 불러올 수 있고 BoardPrincipal.class::cast에서 형변환을 해준다.
    그리고 getUsername을 불러와서 유저 정보를 불러온다.
    
    위 코드의 순서

 */
