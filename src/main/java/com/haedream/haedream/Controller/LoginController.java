package com.haedream.haedream.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(Model model) {

        return "login"; 
    }

    @PostMapping("/login")
    public String login() {
        // 로그인 성공 시 리다이렉트할 경로를 반환
        return "redirect:/main"; // 성공 시 메인 페이지로 리다이렉트
    }
    
    
    
}

