package com.haedream.haedream.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.haedream.haedream.dto.request.LogDTO;
import com.haedream.haedream.service.LoglistService;

import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LoglistController {

    @Autowired
    private LoglistService loglistService;

    @GetMapping("/logList")
    public String logList(Model model) {
        return "loglist";
    }

    @GetMapping("/logListData")
    @ResponseBody
    public List<LogDTO> logListData() {
        List<LogDTO> logList = loglistService.getLogList();
        return logList;
    }

    // 로그 DB에 저장
    @PostMapping("/saveLog")
    public ResponseEntity<LogDTO> saveLog(@RequestBody LogDTO logDTO) {
        LogDTO savedLog = loglistService.saveLog(logDTO);
        return ResponseEntity.ok(savedLog);
    }

    // 로그 삭제
    @DeleteMapping("/logList/{logId}")
    @ResponseBody
    public List<LogDTO> deleteLog(@PathVariable String logId) {
        return loglistService.deleteLog(logId);
    }

}
