package com.haedream.haedream.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.haedream.haedream.dto.request.LogDTO;
import com.haedream.haedream.dto.response.ResLogDTO;
import com.haedream.haedream.entity.Log;
import com.haedream.haedream.entity.UserEntity;
import com.haedream.haedream.repository.LogRepository;
import com.haedream.haedream.repository.UserRepository;

@Service
public class SaveLogService { // API키의 유효성(DB에 있는지 없는지)여부 검사를 포함한 로그 저장용
                              // LogDTO를 Log 엔티티로 변환하고, 저장된 엔티티를 다시 ResLogDTO로 변환
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LogRepository logRepository;

    @SuppressWarnings("null")
    public ResLogDTO saveData(LogDTO dto) {
        // apiKey 유효성검사
        Optional<UserEntity> userEntity = userRepository.findByApiKey(dto.getApiKey());

        if (userEntity.isPresent()) {
            // apiKey가 유효한 경우 : LogDTO -> Log 엔티티 변환
            Log log = Log.builder()
                    .modelName(dto.getModelName())
                    .projectName(dto.getProjectName())
                    .inputData(dto.getInputData())
                    .outputData(dto.getOutputData())
                    .apiKey(dto.getApiKey())
                    .logDate(dto.getLogDate())
                    .build();
            // Log 엔티티를 db에저장
            log = logRepository.save(log);

            // 저장된 Log엔티티 -> ResLogDTO 변환
            return ResLogDTO.builder()
                    .modelName(log.getModelName())
                    .projectName(log.getProjectName())
                    .inputData(log.getInputData())
                    .outputData(log.getOutputData())
                    .logDate(log.getLogDate())
                    .id(log.getId())
                    .build();

        } else {
            // apiKey가 유효하지 않은 경우
            throw new RuntimeException("존재하지 않는 API KEY입니다.");
        }
    }
}
