package com.fastcampus.board_project.dto;

import com.fastcampus.board_project.domain.UserAccount;

public record UserAccountSignUpDto(
        String userId,
        String userPassword,
        String email,
        String memo // Occupation = memo
) {

    public static UserAccountSignUpDto of(String userId, String userPassword, String email, String memo){
        return new UserAccountSignUpDto(userId, userPassword, email, memo);
    }


    public UserAccount toEntity() {
        return UserAccount.of(userId, userPassword, email, null, memo);
    }
}
