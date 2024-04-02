package com.haedream.haedream.controller;

import java.io.IOException;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.haedream.haedream.jwt.JWTUtil;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public LoginController(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login"; 
    }

    @GetMapping("path")
    public String getMethodName(Model model) {
        return new String();
    }
    

    @PostMapping("/login")
    public String loginProcess(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
            
            // 사용자 인증
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            // 로그인 성공한 경우 JWT 생성
            String token = jwtUtil.createJwt(username, "", 60 * 60 * 1000L);
            System.out.println(token);
            // response.addHeader("Authorization", "Bearer " + token);
            Cookie cookie = new Cookie("Authorization", String.format("Bearer %s", token));
            cookie.setPath("/main");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            
            return "redirect:/main";
        }
}