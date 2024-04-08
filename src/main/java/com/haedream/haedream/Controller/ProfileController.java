package com.haedream.haedream.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.haedream.haedream.entity.UserEntity;
import com.haedream.haedream.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile")
    public String profile(Model model, HttpServletRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        UserEntity user = userRepository.findByUsername(username);

        request.getSession().setAttribute("username", user.getUsername());
        request.getSession().setAttribute("email", user.getEmail());
        request.getSession().setAttribute("apiKey", user.getApi_key());

        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("apiKey", user.getApi_key());

        return "profile";
    }
    
}