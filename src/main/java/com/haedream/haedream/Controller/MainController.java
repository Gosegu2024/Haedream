package com.haedream.haedream.controller;

import java.util.List;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.security.core.context.SecurityContextHolder;

import com.haedream.haedream.entity.UserEntity;
import com.haedream.haedream.model.Project;
import com.haedream.haedream.repository.UserRepository;
import com.haedream.haedream.service.ProjectService;

@Controller
@SessionAttributes({ "username", "api_key" })
public class MainController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/main")
    public String mainPage(Model model) {
        // 현재 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity user = userRepository.findByUsername(username);
        String apiKey = user.getApi_key();

        List<Project> projects = projectService.findProjectsByOwner(username);

        model.addAttribute("username", username);
        model.addAttribute("apiKey", apiKey);
        model.addAttribute("projects", projects);
        model.addAttribute("newProject", new Project());

        return "main";
    }

    @PostMapping("/main/projectSave")
    public String projectSave(Model model, @RequestParam("projectName") String projectName,
            @RequestParam("standard") String standard) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        model.addAttribute("username", username);

        if (projectName != null && standard != null) {
            Project project = new Project();
            project.setProjectName(projectName);
            project.setStandard(standard);
            project.setOwner(username);

            projectService.projectSave(project);

        } else {

            System.out.println("프로젝트 이름 또는 평가 기준이 없습니다.");
        }

        return "redirect:/main";
    }

    
}