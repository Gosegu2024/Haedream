package com.haedream.haedream.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.haedream.haedream.Model.projects;
import com.haedream.haedream.Service.projectsService;

@Controller
public class MainController {

    private final projectsService projectsService;

    @Autowired
    public MainController(projectsService projectsService) {
        this.projectsService = projectsService;
    }

    @GetMapping("/main")
    public String projectList(Model model) {
        List<projects> projects = projectsService.findAllprojects();
        model.addAttribute("projects", projects);
        return "main";
    }

}
