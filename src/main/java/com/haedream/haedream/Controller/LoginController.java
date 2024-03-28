package com.haedream.haedream.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    // 로그인으로 이동 
    @GetMapping("/login")
    public String loginForm(){

        return "login";
    }
   
    // 로그인 처리
    @PostMapping("/loginProcess")
    public String loginProcess(@RequestParam("username")String username, @RequestParam("password")String password){

        return "redirect:/";
    }
}
