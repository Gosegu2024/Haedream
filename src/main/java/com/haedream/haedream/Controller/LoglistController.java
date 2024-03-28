package com.haedream.haedream.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.haedream.haedream.model.Project;
import com.haedream.haedream.service.ProjectService;

@Controller
public class LoglistController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/logList")
    public String logList(Model model) {
        return "loglist";
    }
}
