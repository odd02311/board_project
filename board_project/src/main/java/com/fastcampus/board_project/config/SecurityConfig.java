package com.fastcampus.board_project.config;


import com.fastcampus.board_project.dto.UserAccountDto;
import com.fastcampus.board_project.dto.security.BoardPrincipal;
import com.fastcampus.board_project.repository.UserAccountRepository;
import com.fastcampus.board_project.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

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
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/articles/{articleId}/form").authenticated()
                        .anyRequest().permitAll())
                .formLogin(form -> form.loginProcessingUrl("/user/login").permitAll()
                        .usernameParameter(
                                "userId").passwordParameter("password")
                        .successHandler(customAuthenticationSuccessHandler())

                )
                .logout(logout -> logout
                        .logoutUrl("/user/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")// 로그아웃 경로
                        .logoutSuccessUrl("/")      // 로그아웃 후 리다이렉트 경로
                        .permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
//
//    @Bean
//    public UserDetailsService userDetailsService(UserAccountRepository userAccountRepository) {
//        return username -> userAccountRepository
//                .findById(username)
//                .map(UserAccountDto::from)
//                .map(BoardPrincipal::from)
//                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다 - username: " + username));
//
//
//    }
//
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }
}