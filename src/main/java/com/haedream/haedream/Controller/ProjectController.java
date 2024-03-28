package com.haedream.haedream.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.haedream.haedream.model.Project;
import com.haedream.haedream.service.ProjectService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping("/main")
    public String projectForm(Model model) {
        model.addAttribute("projects", projectService.findAll());
        return "main";
    }

    @PostMapping("/project/save")
    public String saveProject(Project project) {
        projectService.saveProject(project.getProjectName());
        return "redirect:/main";
    }

    @PostMapping("/project/delete")
    public String deleteProjects(@RequestParam("projectIds") List<String> projectIds) {
        projectService.deleteProjects(projectIds);
        return "redirect:/main";
    }
}
