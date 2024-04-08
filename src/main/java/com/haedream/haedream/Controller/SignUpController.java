package com.haedream.haedream.controller;

import com.haedream.haedream.dto.SignUpDTO;
import com.haedream.haedream.service.SignUpService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SignUpController {

    private final SignUpService signupService;

    public SignUpController(SignUpService signupService) {
        this.signupService = signupService;
    }

    @PostMapping("/signup")
    public String signupProcess(SignUpDTO signupDTO, RedirectAttributes redirectAttributes) {
        try {
            signupService.signupProcess(signupDTO);
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/login";
        }
        return "redirect:/login";
    }
}
