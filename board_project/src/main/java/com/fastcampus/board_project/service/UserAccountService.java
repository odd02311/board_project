package com.fastcampus.board_project.service;

import com.fastcampus.board_project.domain.UserAccount;
import com.fastcampus.board_project.dto.UserAccountDto;
import com.fastcampus.board_project.dto.UserAccountSignUpDto;
import com.fastcampus.board_project.repository.UserAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional(readOnly = true)
    public UserAccountDto authenticate(String userId, String password) {

        log.info(userId);

        System.out.println("Service - Received userId: " + userId);
        System.out.println("Service - Received password: " + password);

        return userAccountRepository.findByUserId(userId)
                .map(UserAccountDto::from)
                .filter(user -> passwordEncoder.matches(password, user.userPassword()))
                .orElseThrow(() -> new EntityNotFoundException("아이디 또는 비밀번호가 잘못되었습니다 - userId: " + userId +" - password"+ password));
    }

    public UserAccountDto signUp(UserAccountSignUpDto signUpDto) {
        if (userAccountRepository.findByUserId(signUpDto.userId()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다: " + signUpDto.userId());
        }

        UserAccount newUser = signUpDto.toEntity();
        newUser.setUserPassword(passwordEncoder.encode(newUser.getUserPassword()));
        userAccountRepository.save(newUser);

        return UserAccountDto.from(newUser);
    }
}
