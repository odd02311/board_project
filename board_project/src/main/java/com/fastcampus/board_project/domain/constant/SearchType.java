package com.fastcampus.board_project.domain.constant;


import lombok.Getter;

public enum SearchType {
    TITLE("제목"),
    CONTENT("본문"),
    ID("유저 ID"),
    NICKNAME("닉네임"),
    HASHTAG("해시태그");

    // 대소문자 구분 없이 처리할 수 있는 변환 메서드를 추가
    public static SearchType fromString(String value) {
        for (SearchType type : SearchType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + value);
    }

    @Getter
    private final String description;

    SearchType(String description) {
        this.description = description;
    }
}
