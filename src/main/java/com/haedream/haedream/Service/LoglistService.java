package com.haedream.haedream.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haedream.haedream.dto.request.LogDTO;
import com.haedream.haedream.entity.Log;
import com.haedream.haedream.repository.LogRepository;

@Service
public class LoglistService { // SaveLogService를 통해 DB 저장된 로그 데이터를 삭제하고 조회하는 역할

    @Autowired
    private LogRepository logRepository;

    public List<LogDTO> getLogList() {
        List<Log> logs = logRepository.findAll();
        return logs.stream().map(this::convertToLogDTO).collect(Collectors.toList());
    }

    // 로그엔티티 ->로그DTO
    private LogDTO convertToLogDTO(Log log) {
        return new LogDTO(
                log.getModelName(),
                log.getProjectName(),
                log.getInputData(),
                log.getOutputData(),
                log.getApiKey(),
                log.getLogDate(),
                log.getId());
    }

    // 로그 삭제
    @SuppressWarnings("null")
    public List<LogDTO> deleteLog(String logId) {
        logRepository.deleteById(logId);
        return getLogList();
    }

}
