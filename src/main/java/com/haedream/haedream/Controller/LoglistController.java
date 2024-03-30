package com.haedream.haedream.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.haedream.haedream.dto.request.LogDTO;
import com.haedream.haedream.service.LoglistService;

@RestController // @ResponseBody+@Controller 결합된 형태, 이거쓰면 Responsbody를 지워도 됨
public class LoglistController {

    @Autowired
    private LoglistService loglistService;

    @GetMapping("/logList")
    public String logList(Model model) {
        return "loglist";
    }

    @GetMapping("/logListData")
    public List<LogDTO> logListData() {
        return loglistService.getLogList();
    }

    // 로그 삭제
    @DeleteMapping("/logList/{logId}")
    public List<LogDTO> deleteLog(@PathVariable String logId) {
        return loglistService.deleteLog(logId);
    }

}
