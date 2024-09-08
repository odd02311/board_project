package com.fastcampus.board_project.repository;

import com.fastcampus.board_project.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
    Optional<UserAccount> findByUserId(String userId);
}