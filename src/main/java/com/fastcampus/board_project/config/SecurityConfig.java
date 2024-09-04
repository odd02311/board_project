package com.fastcampus.board_project.config;


import com.fastcampus.board_project.dto.UserAccountDto;
import com.fastcampus.board_project.dto.security.BoardPrincipal;
import com.fastcampus.board_project.repository.UserAccountRepository;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

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
      return  http
              .authorizeHttpRequests(auth -> auth
                      .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                      .requestMatchers("/api/**").permitAll()
                      .requestMatchers(
                              HttpMethod.GET,
                              "/",
                              "/articles",
                              "/articles/search-hashtag"
                      ).permitAll()
                      .anyRequest().authenticated()
              )
              .formLogin(withDefaults())
              .logout(logout -> logout.logoutSuccessUrl("/"))
              .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
              .build();

  }

  @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

      return(web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  }

  @Bean
    public UserDetailsService userDetailsService(UserAccountRepository userAccountRepository) {
      return username -> userAccountRepository
              .findById(username)
              .map(UserAccountDto::from)
              .map(BoardPrincipal::from)
              .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다 - username: " + username));


  }

  @Bean
    public PasswordEncoder passwordEncoder() {
      return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }


}
