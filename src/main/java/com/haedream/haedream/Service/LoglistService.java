package com.haedream.haedream.service;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.haedream.haedream.entity.Log;
import com.haedream.haedream.repository.LogRepository;
import com.haedream.haedream.util.DateUtils;

@Service
public class LoglistService { // SaveLogService를 통해 DB 저장된 로그 데이터를 삭제하고 조회하는 역할

    @Autowired
    private LogRepository logRepository;

    public List<Log> getLogList(String apikey, String projectName) {
        Sort sort = Sort.by(Sort.Direction.DESC, "logDate");
        return logRepository.findByApiKeyAndProjectNameAndIsItEval(apikey, projectName, "N", sort);
    }

    // 로그 삭제
    public void deleteLogsByApiKeyAndProjectNameAndId(String apiKey, String projectName, String id) {       
        logRepository.deleteByApiKeyAndProjectNameAndId(apiKey, projectName, id);
    }

    public List<Log> getLogListEvaluated(String apiKey, String projectName) {
        Sort sort = Sort.by(Sort.Direction.DESC, "logDate");
        return logRepository.findByApiKeyAndProjectNameAndIsItEval(apiKey, projectName, "Y", sort);
    }

    // 평가여부 업데이트 "Y"
    public void updateIsItEvalY(String logId) {
        Log log = logRepository.findById(logId).get();
        log.setIsItEval("Y");
        logRepository.save(log);
    }

    // 평가여부 업데이트 "N"
    public void updateIsItEvalN(String logId) {
        Log log = logRepository.findById(logId).orElse(null);
        if(log != null){
            log.setIsItEval("N");
            logRepository.save(log);
        }
    }

    public List<Map<String, String>> logListFormat(List<Log> logList) {
        List<Map<String, String>> formattedLogs = new ArrayList<>();
        for (Log log : logList) {
            Map<String, String> logDetails = new HashMap<>();
            logDetails.put("projectName", log.getProjectName());
            logDetails.put("modelName", log.getModelName());
            logDetails.put("logDate", DateUtils.formatZonedDateTime(log.getLogDate()));
            logDetails.put("id", log.getId());
            logDetails.put("apiKey", log.getApiKey());
            formattedLogs.add(logDetails);
        }
        return formattedLogs;
    }

}
