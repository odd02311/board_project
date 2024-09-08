package com.fastcampus.board_project.config;

import com.fastcampus.board_project.domain.CustomUserDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Authentication 객체에서 사용자 정보를 가져옴
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUserAccount().getUserId();

        // 세션에 userId 값 저장
        HttpSession session = request.getSession();
        session.setAttribute("userId", userId);

        // 로그인 성공 후 리다이렉트
        response.sendRedirect("/");
    }
}
