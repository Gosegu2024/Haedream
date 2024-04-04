package com.haedream.haedream.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.haedream.haedream.dto.request.LogDTO;
import com.haedream.haedream.dto.response.ResLogDTO;
import com.haedream.haedream.service.SaveLogService;

@Controller
public class SaveLogController {
    @Autowired
    private SaveLogService saveLogService;

    // 로그 db에 저장
    @PostMapping("/save_data")
    public ResponseEntity<ResLogDTO> saveData(@RequestParam String js) {
        ResLogDTO saveLogDTO = saveLogService.saveData(LogDTO.parse(js));

        return ResponseEntity.status(HttpStatus.CREATED).body(saveLogDTO);
    }
}
