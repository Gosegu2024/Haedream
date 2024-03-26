package com.haedream.haedream.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoglistController {
    @GetMapping("/logList")
    public String logList() {
        return "loglist";
    }
}
