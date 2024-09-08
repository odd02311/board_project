package com.fastcampus.board_project.dto.request;

import com.fastcampus.board_project.dto.UserAccountDto;

public record UserAccountSignUpRequest(
        String userId,
        String userPassword,
        String email,
        String memo // Occupation 값
) {

    public static UserAccountSignUpRequest of(String userId, String userPassword, String email, String memo) {
        return new UserAccountSignUpRequest(userId, userPassword, email, memo);
    }

    public UserAccountDto toDto() {
        return UserAccountDto.of(
                userId,
                userPassword,
                email,
                null, // nickname 생략
                memo  // Occupation 값으로 memo 사용
        );
    }
}
