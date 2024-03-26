package com.haedream.haedream.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LandingController {
    @GetMapping("/landing")
    public String landing() {
        return "landing";
    }
}
