package com.haedream.haedream.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

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
        public RoleHierarchy roleHierarchy() {

                RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();

                hierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

                return hierarchy;
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                http
                                .formLogin((auth) -> auth.loginPage("/login")
                                        .loginProcessingUrl("/loginProcess")
                                        .defaultSuccessUrl("/main") // 로그인 성공 시 이동할 기본 URL 설정
                                        .permitAll());
                http
                                        .logout((logout) -> logout
                                        .logoutUrl("/logout") // 로그아웃 URL 설정
                                        .logoutSuccessUrl("/login") // 로그아웃 성공 후 이동할 URL 설정
                                        .permitAll());

                http
                                .authorizeHttpRequests((auth) -> auth
                                        .requestMatchers("/", "/login", "/signup").permitAll()
                                        .requestMatchers("/css/**","/img/**","/js/**").permitAll()

                                        .requestMatchers("/propile/**").hasAnyRole("ADMIN", "USER")
                                        .requestMatchers("/admin").hasAnyRole("ADMIN")
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