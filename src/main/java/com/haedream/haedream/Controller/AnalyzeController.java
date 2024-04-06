package com.haedream.haedream.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AnalyzeController {

  @GetMapping("/analyze")
  public String analyze(Model model, @RequestParam(required = false) String projectName,
      @RequestParam(required = false) String apiKey) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    model.addAttribute("username", username);
    model.addAttribute("projectName", projectName);
    model.addAttribute("apiKey", apiKey);
    return "analyze";
  }

}
