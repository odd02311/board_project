package com.fastcampus.board_project.config;

import com.fastcampus.board_project.domain.UserAccount;
import com.fastcampus.board_project.repository.UserAccountRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Import(SecurityConfig.class)
public class TestSecurityConfig {
    @MockBean
    private UserAccountRepository userAccountRepository;

    @BeforeTestMethod
    public void securitySetUp() {   // 테스트 전용 계정 생성
        given(userAccountRepository.findById(anyString())).willReturn(Optional.of(UserAccount.of(
                "TestAuthor",
                "password",
                "author-test@email.com",
                "testNickname",
                "testMemo"
        )));
    }


}


/*
    anyString() == 어떤 문자열이든 받는다

 */