package com.haedream.haedream.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.haedream.haedream.entity.Log;
import com.haedream.haedream.repository.LogRepository;

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

}
