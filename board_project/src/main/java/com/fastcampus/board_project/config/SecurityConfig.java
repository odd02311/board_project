package com.fastcampus.board_project.config;


import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//      return http
//              .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // 권한을 받아서 어떤 request든 다 열어줌
//              .formLogin((formLogin) -> formLogin
//                  .loginPage("/user/login"))
//                   .build();

//      return http
//          .authorizeHttpRequests(auth -> auth
//              .requestMatchers(
//                  PathRequest.toStaticResources().atCommonLocations()
//              ).permitAll()
//              .requestMatchers(HttpMethod.GET,
//                  "/",
//                  "/articles",
//                  "/articles/{articleId}",
//                  "/articles/detail",
//                  "articles/detail/{articleId}",
//                  "/articles/search-hashtag").permitAll()
//              .anyRequest().authenticated())
//          .formLogin(Customizer.withDefaults())
//          .logout(logout -> logout
//              .logoutSuccessUrl("/").permitAll()).build();
//  }
      return http
              .authorizeHttpRequests(auth -> auth
                      .requestMatchers("/login").permitAll() // 로그인 페이지는 접근 허용
                      .anyRequest().permitAll()  // 다른 모든 요청도 접근 허용
              )
              .formLogin(formLogin -> formLogin
                      .loginPage("/login")  // 로그인 페이지의 URL 설정
                      .permitAll()  // 로그인 페이지 접근은 인증 없이 허용
              )
              .logout(LogoutConfigurer::permitAll  // 로그아웃은 인증 없이 허용
              )
              .csrf((csrf) -> csrf.disable())
              .build();
  }
}
