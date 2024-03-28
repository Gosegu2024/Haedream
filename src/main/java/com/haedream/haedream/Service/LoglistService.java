package com.haedream.haedream.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haedream.haedream.dto.request.LogDTO;
import com.haedream.haedream.entity.Log;
import com.haedream.haedream.repository.LogRepository;

@Service
public class LoglistService {

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

    // 로그 DB에 저장
    public LogDTO saveLog(LogDTO logDTO) {
        // DTO -> 엔티티
        Log log = new Log();
        log.setModelName(logDTO.getModelName());
        log.setProjectName(logDTO.getProjectName());
        log.setInputData(logDTO.getInputData());
        log.setOutputData(logDTO.getOutputData());
        log.setApiKey(logDTO.getApiKey());
        log.setLogDate(logDTO.getLogDate());

        // 엔티티 DB에 저장
        log = logRepository.save(log);

        // 엔티티 -> DTO 변환 뒤 반환하기
        LogDTO savedLogDTO = new LogDTO();
        savedLogDTO.setId(log.getId()); // MongoDB에서 자동으로 생성되는 고유 ID
        savedLogDTO.setModelName(log.getModelName());
        savedLogDTO.setProjectName(log.getProjectName());
        savedLogDTO.setInputData(log.getInputData());
        savedLogDTO.setOutputData(log.getOutputData());
        savedLogDTO.setApiKey(log.getApiKey());
        savedLogDTO.setLogDate(log.getLogDate());

        return savedLogDTO;

    }

    // 로그 삭제
    public List<LogDTO> deleteLog(String logId) {
        logRepository.deleteById(logId);
        return getLogList();
    }

}
