package com.fastcampus.board_project.domain.constant;

import lombok.Getter;

public enum RoleType {
    USER("ROLE_USER"),
    ADMIN_USER("ROLE_ADMIN");

    @Getter
    private final String name;

    RoleType(String name) {
        this.name = name;
    }

}
