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
                                        .addHeaderWriter(new StaticHeadersWriter("X-Content-Type-Options", "nosniff"))
                                        .addHeaderWriter(new StaticHeadersWriter("X-Frame-Options", "SAMEORIGIN"))
                                        .addHeaderWriter(new StaticHeadersWriter("X-XSS-Protection", "1; mode=block")));

                http
                                .formLogin((auth) -> auth.loginPage("/login")
                                                .loginProcessingUrl("/loginProcess")
                                                .defaultSuccessUrl("/main") 
                                                .successHandler((request, response, authentication) -> {
                                                        Collection<? extends GrantedAuthority> authorities = authentication
                                                                        .getAuthorities();
                                                        for (GrantedAuthority authority : authorities) {
                                                                if (authority.getAuthority().equals("ROLE_ADMIN")) {
                                                                        response.sendRedirect("/admin");
                                                                } else {
                                                                        response.sendRedirect("/main");
                                                                }
                                                        }
                                                })
                                                .permitAll());

                http
                                .logout((logout) -> logout
                                                .logoutUrl("/logout") 
                                                .logoutSuccessUrl("/") 
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
                                                .maximumSessions(1) 
                                                .maxSessionsPreventsLogin(true));
                                                
                http
                                .sessionManagement((auth) -> auth
                                                .sessionFixation().changeSessionId());

                return http.build();
        }

}
