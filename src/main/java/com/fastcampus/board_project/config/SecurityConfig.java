package com.fastcampus.board_project.config;


import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//      return http
//              .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // 권한을 받아서 어떤 request든 다 열어줌
//              .formLogin((formLogin) -> formLogin
//                  .loginPage("/user/login"))
//                   .build();

      return http
          .authorizeHttpRequests(auth -> auth
              .requestMatchers(
                  PathRequest.toStaticResources().atCommonLocations()
              ).permitAll()
              .requestMatchers(HttpMethod.GET,
                  "/",
                  "/articles",
                  "/articles/{articleId}",
                  "/articles/detail",
                  "articles/detail/{articleId}",
                  "/articles/search-hashtag").permitAll()
              .anyRequest().authenticated())
          .formLogin(Customizer.withDefaults())
          .logout(logout -> logout
              .logoutSuccessUrl("/").permitAll()).build();
  }
}
