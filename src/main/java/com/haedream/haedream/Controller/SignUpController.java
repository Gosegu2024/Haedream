package com.haedream.haedream.controller;

import com.haedream.haedream.dto.SignUpDTO;
import com.haedream.haedream.service.SignUpService;

import java.util.DuplicateFormatFlagsException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignUpController {

    private final SignUpService signupService;

    public SignUpController(SignUpService signupService) {
        this.signupService = signupService;
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String signupProcess(SignUpDTO signupDTO) {
        System.out.println(signupDTO.getUsername());
        System.out.println(signupDTO.getPassword());
        System.out.println(signupDTO.getEmail());

        try {
            signupService.signupProcess(signupDTO);

        } catch (DuplicateFormatFlagsException e) {

            System.out.println("이미 사용중인 ID입니다.");
        }

        return "redirect:/login";
    }
}