package com.haedream.haedream.config;

import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder() {

                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                http
                                .formLogin((auth) -> auth.loginPage("/login")
                                                .loginProcessingUrl("/loginProcess")
                                                .defaultSuccessUrl("/main") // 로그인 성공 시 이동할 기본 URL 설정
                                                .successHandler((request, response, authentication) -> {
                                                        // 로그인 한 사용자의 권한 확인
                                                        Collection<? extends GrantedAuthority> authorities = authentication
                                                                        .getAuthorities();
                                                        for (GrantedAuthority authority : authorities) {
                                                                if (authority.getAuthority().equals("ROLE_ADMIN")) {
                                                                        // 관리자인 경우 "/admin" 페이지로 이동
                                                                        response.sendRedirect("/admin");
                                                                } else {
                                                                        // 일반 사용자인 경우 "/main" 페이지로 이동
                                                                        response.sendRedirect("/main");
                                                                }
                                                        }
                                                })
                                                .permitAll());

                http
                                .logout((logout) -> logout
                                                .logoutUrl("/logout") // 로그아웃 URL 설정
                                                .logoutSuccessUrl("/") // 로그아웃 성공 후 이동할 URL 설정
                                                .permitAll());

                http
                                .authorizeHttpRequests((auth) -> auth
                                                .requestMatchers("/", "/login", "/signup", "/save_data", "/save_eval")
                                                .permitAll()
                                                .requestMatchers("/css/**", "/img/**", "/js/**").permitAll()
                                                .requestMatchers("/admin").hasRole("ADMIN")
                                                .anyRequest().authenticated());

                http
                                .csrf((auth) -> auth.disable());

                http
                                .sessionManagement((auth) -> auth
                                                .maximumSessions(1) // 최대 허용 가능 세션 수
                                                .maxSessionsPreventsLogin(true)); // 동시 로그인 차단

                return http.build();
        }

}