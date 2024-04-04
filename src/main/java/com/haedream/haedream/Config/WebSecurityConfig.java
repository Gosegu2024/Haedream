// package com.haedream.haedream.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import
// org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import
// org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// @EnableWebSecurity

// public class WebSecurityConfig { // 권한별로, 조건별로 접근할 수 있는 페이지 지정하는 곳 : 현준이 로그인
// 기능 완성하면 풀받아서 수정 후 커밋푸쉬하기!!

// // PasswordEncoder Bean 정의
// @Bean
// public PasswordEncoder passwordEncoder() {
// return new BCryptPasswordEncoder();
// }

// // SecurityFilterChain Bean 정의
// @Bean
// public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
// http
// // CSRF보호 비활성화 : 인증방식으로 jwt를 쓰면 서버측에서 사용자 세션 유지 ㄴㄴ함, 그래서 추가적인 복잡성을 없애기 위해
// 비활성화
// .csrf().disable()
// // 로그인시 설정
// .formLogin()
// .loginPage("/login") // 우리가 정한 로그인 페이지URL
// .defaultSuccessUrl("/main", true) // 로그인 성공시 리다이렉트할 URL
// .permitAll() // 모든 사용자가 로그인 페이지 접근가능하게
// .and()
// // 로그아웃시 설정
// .logout()
// .logoutSuccessUrl("/") // 로그아웃 성공시 랜딩페이지로 이동
// .permitAll()
// .and()
// // URL별 접근권한 설정
// .authorizeRequests()
// .antMatchers("/", "/css/**", "/js/**").permitAll() // 랜딩페이지 : 누구나 접근 허용
// .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN") // ADMIN 권한을 가진 사용자만
// /admin 접근 허용
// .anyRequest().authenticated(); // 나머지 URL은 로그인한 사용자만 접근 허용
// return http.build();
// }
// }
