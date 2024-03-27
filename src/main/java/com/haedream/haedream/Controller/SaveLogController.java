package com.haedream.haedream.controller;

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
    @PostMapping("/save_data")
    public ResponseEntity<ResLogDTO> saveData(@RequestParam String js){
        // System.out.println("controller");
        // System.out.println(js);
        ResLogDTO saveLogDTO= SaveLogService.saveData(LogDTO.parse(js));
        System.out.println("전체 데이터: " + saveLogDTO.toString());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(saveLogDTO);
    }
}
