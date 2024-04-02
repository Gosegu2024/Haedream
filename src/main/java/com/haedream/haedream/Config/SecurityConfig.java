package com.haedream.haedream.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.haedream.haedream.jwt.JWTFilter;
import com.haedream.haedream.jwt.JWTUtil;
import com.haedream.haedream.jwt.LoginFilter;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        // AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
        private final AuthenticationConfiguration authenticationConfiguration;
        private final JWTUtil jwtUtil;

        public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil) {

                this.authenticationConfiguration = authenticationConfiguration;
                this.jwtUtil = jwtUtil;
        }

        // AuthenticationManager Bean 등록
        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

                return configuration.getAuthenticationManager();
        }

        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder() {

                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                http
                                .cors((corsCustomizer -> corsCustomizer
                                                .configurationSource(new CorsConfigurationSource() {

                                                        @SuppressWarnings("null")
                                                        @Override
                                                        public CorsConfiguration getCorsConfiguration(
                                                                        HttpServletRequest request) {

                                                                CorsConfiguration configuration = new CorsConfiguration();

                                                                // 허용할 프론트엔드 서버
                                                                configuration.setAllowedOrigins(Collections
                                                                                .singletonList("http://localhost:8088"));
                                                                // 허용할 메소스 지정
                                                                configuration.setAllowedMethods(
                                                                                Collections.singletonList("*"));
                                                                configuration.setAllowCredentials(true);
                                                                // 허용할 헤더
                                                                configuration.setAllowedHeaders(
                                                                                Collections.singletonList("*"));
                                                                // 허용 시간
                                                                configuration.setMaxAge(3600L);
                                                                // 클라이언트로 보낼 시 Authorization에 JWT 넣기
                                                                configuration.setExposedHeaders(Collections
                                                                                .singletonList("Authorization"));

                                                                return configuration;
                                                        }
                                                })));

                http
                                .csrf((auth) -> auth.disable());

                http
                                .formLogin((auth) -> auth.disable());

                http
                                .httpBasic((auth) -> auth.disable());

                http
                                .authorizeHttpRequests((auth) -> auth
                                                .requestMatchers("/main").authenticated() // /main 페이지에 대한 접근은 인증된 사용자에게만 허용
                                                .requestMatchers("/admin").hasRole("ADMIN")
                                                .requestMatchers("/css/**", "/img/**", "/js/**").permitAll()
                                                .requestMatchers("/", "/login", "/signup").permitAll()
                                                .anyRequest().authenticated());

                http
                                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);

                // 필터 추가 LoginFilter()는 인자를 받음 (AuthenticationManager() 메소드에
                // authenticationConfiguration 객체를 넣어야 함) 따라서 등록 필요
                http
                                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration),
                                                jwtUtil), UsernamePasswordAuthenticationFilter.class);

                http
                                .sessionManagement((session) -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

                return http.build();
        }
}