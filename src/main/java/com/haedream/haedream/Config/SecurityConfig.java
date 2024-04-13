package com.haedream.haedream.config;

import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

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
                                .headers(headers -> headers
                                        // X-Content-Type-Options (MINE 타입 스니핑 방지)
                                        .addHeaderWriter(new StaticHeadersWriter("X-Content-Type-Options", "nosniff"))// 웹 브라우저에게 서버가 제공한 MINE타입을 강제
                                        // X-Frame-Options (클릭재킹 방지)
                                        .addHeaderWriter(new StaticHeadersWriter("X-Frame-Options", "SAMEORIGIN"))// 프레임 내 페이지 로드하는 것을 동일한 출처의 경우에만 허용
                                        // X-XSS-Protection (XSS 공격 방지)
                                        .addHeaderWriter(new StaticHeadersWriter("X-XSS-Protection", "1; mode=block")));// 1 = 활성화

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
                                                .maximumSessions(1) // 하나의 아이디에 대한 다중 로그인 허용 가능 수
                                                .maxSessionsPreventsLogin(true));
                                                // 다중 로그인 개수를 초과한 경우(true:새로운 로그인 차단, false:기존 세션 하나 삭제)
                                                
                http
                                .sessionManagement((auth) -> auth
                                                .sessionFixation().changeSessionId());
                                                // 로그인 시 동일한 세션에 대한 id를 변경(세션 고정 공격 보호)

                return http.build();
        }

}
