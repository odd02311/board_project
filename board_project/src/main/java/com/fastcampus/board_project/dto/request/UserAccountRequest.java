package com.fastcampus.board_project.dto.request;


import com.fastcampus.board_project.dto.UserAccountDto;

public record UserAccountRequest(
        String userId,
        String password
) {
    public static UserAccountRequest of(String userId, String password) {
        return new UserAccountRequest(userId, password);
    }

    public UserAccountDto toDto() {
        return UserAccountDto.of(
                userId,       // userId
                password,     // userPassword
                null,         // email (생략 또는 별도의 값을 채워야 함)
                null,         // nickname (생략 또는 별도의 값을 채워야 함)
                null          // memo (생략 또는 별도의 값을 채워야 함)
        );
    }
}