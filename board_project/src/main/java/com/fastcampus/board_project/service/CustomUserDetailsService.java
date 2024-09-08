package com.fastcampus.board_project.service;

import com.fastcampus.board_project.domain.CustomUserDetails;
import com.fastcampus.board_project.domain.UserAccount;
import com.fastcampus.board_project.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserAccountRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("Authenticating user: " + username);  // 로그 확인

       UserAccount userAccount =
               userAccountRepository.findByUserId(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        return new CustomUserDetails(userAccount);
    }
}
