package com.haedream.haedream.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.haedream.haedream.entity.Eval;
import com.haedream.haedream.service.AnalyzeService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AnalyzeController {

  @Autowired
  AnalyzeService analyzeService;

  @GetMapping("/analyze")
  public String analyze(HttpSession session, Model model) {
    String projectName = (String) session.getAttribute("projectName");
    String username = (String) session.getAttribute("username");

    if (projectName == null || username == null) {
      return "redirect:/main";
    }
    List<Eval> evalList = analyzeService.findByProjectNameAndUsernameOrderByEvalLogDateDesc(projectName, username);

    model.addAttribute("evalList", evalList);

    return "analyze";
  }

}
