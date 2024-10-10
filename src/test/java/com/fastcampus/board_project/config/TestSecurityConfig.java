package com.fastcampus.board_project.config;

import com.fastcampus.board_project.dto.UserAccountDto;
import com.fastcampus.board_project.service.UserAccountService;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Import(SecurityConfig.class)
public class TestSecurityConfig {
    @MockBean
    private UserAccountService userAccountService;

    @BeforeTestMethod
    public void securitySetUp() {   // 테스트 전용 계정 생성
        given(userAccountService.searchUser(anyString()))
                .willReturn(Optional.of(createUserAccountDto()));

        given(userAccountService.saveUser(anyString(), anyString(), anyString(), anyString(), anyString()))
                .willReturn(createUserAccountDto());
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                "TestAuthor",
                "password",
                "author-test@email.com",
                "testNickname",
                "testMemo"
        );
    }

}


/*
    anyString() == 어떤 문자열이든 받는다

 */