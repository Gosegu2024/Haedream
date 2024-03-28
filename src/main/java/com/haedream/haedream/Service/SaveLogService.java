package com.haedream.haedream.service;

import org.springframework.stereotype.Service;

import com.haedream.haedream.dto.request.LogDTO;
import com.haedream.haedream.dto.response.ResLogDTO;
import com.haedream.haedream.entity.Log;

@Service
public class SaveLogService {
    public static ResLogDTO saveData(LogDTO dto){
        // System.out.println("service");
        // System.out.println(dto.getApiKey());
        Log log = LogDTO.ofEntity(dto);
        
        return ResLogDTO.fromEntity(log);
    }
}
