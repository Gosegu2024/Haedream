package com.haedream.haedream.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoglistController {

    @GetMapping("/logList")
    public String logList(Model model) {
        return "loglist";
    }
}
