package com.haedream.haedream.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.haedream.haedream.dto.LoginDTO;
import com.haedream.haedream.entity.UserEntity;
import com.haedream.haedream.jwt.JWTUtil;
import com.haedream.haedream.repository.UserRepository;

@Controller
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    // 로그인 페이지로 이동
    @GetMapping("/login")
    public String LoginForm() {
 
        return "login";
    }

    // 로그인을 처리
    @PostMapping("/login")
    public String loginProcess(@RequestBody LoginDTO loginDTO, Model model) {
        try {
            // Spring Security의 AuthenticationManager를 사용하여 사용자를 인증
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
            );

            // 인증에 성공하면 인증된 사용자 정보를 가져옴
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            System.out.println(userDetails);

            // UserRepository를 사용하여 사용자를 조회
            UserEntity user = userRepository.findByUsername(loginDTO.getUsername());

            // JWT 토큰 생성
            String jwtToken = jwtUtil.createJwt(user.getUsername(), user.getRole(), 60 * 60 * 10L); // 10 hours expiration time

            // 모델에 JWT 토큰 추가
            model.addAttribute("jwtToken", jwtToken);

            // 로그인 성공 후 홈페이지로 리다이렉트
            return "redirect:/";

        } catch (BadCredentialsException e) {
            // 인증에 실패하면 에러 메시지를 모델에 추가하여 다시 로그인 페이지로 이동
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }
}