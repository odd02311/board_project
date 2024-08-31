package com.fastcampus.board_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {
    @Bean   // 아직 인증 기능이 구현되지않았기 때문에 생상자에 임의로 이름을 넣어주는 환경설정
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("kim"); // TODO: SPRING SECURITY로 인증 기능을 붙이게 될때, 수정

    }
}
