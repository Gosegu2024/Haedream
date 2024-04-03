package com.haedream.haedream.controller;

import java.util.Collection;
import java.util.List;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.haedream.haedream.model.Project;
import com.haedream.haedream.service.ProjectService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/main")
    public String mainPage(Model model, HttpSession session) {

        // 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
  

        // 세션에 사용자 정보 저장
        session.setAttribute("username", username);
         
        System.out.println(username);

        List<Project> projects = projectService.getProjects();
        model.addAttribute("projects", projects);
        model.addAttribute("newProject", new Project());
        return "main";
    }

    @PostMapping("/main/projectSave")
    public String projectSave(String projectName, String standard, HttpSession session) {

        // 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // 세션에 사용자 정보 저장
        session.setAttribute("username", username); 

        Project project = new Project();
        project.setProjectName(projectName);
        project.setStandard(standard);
        projectService.projectSave(project);
        return "redirect:/main";
    }

    
}
