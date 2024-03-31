package com.haedream.haedream.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.haedream.haedream.model.Project;
import com.haedream.haedream.service.ProjectService;

@Controller
public class MainController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/main")
    public String mainPage(Model model) {
        List<Project> projects = projectService.getProjects();
        model.addAttribute("projects", projects);
        model.addAttribute("newProject", new Project());
        return "main";
    }

    @PostMapping("/main/projectSave")
    public String projectSave(String projectName, String standard) {
        Project project = new Project();
        project.setProjectName(projectName);
        project.setStandard(standard);
        projectService.projectSave(project);
        return "redirect:/main";
    }

}
