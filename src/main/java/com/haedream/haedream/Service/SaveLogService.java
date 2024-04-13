package com.haedream.haedream.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.haedream.haedream.dto.request.LogDTO;
import com.haedream.haedream.dto.response.ResLogDTO;
import com.haedream.haedream.entity.Log;
import com.haedream.haedream.entity.UserEntity;
import com.haedream.haedream.model.Project;
import com.haedream.haedream.repository.LogRepository;
import com.haedream.haedream.repository.ProjectRepository;
import com.haedream.haedream.repository.UserRepository;

@Service
public class SaveLogService { 
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LogRepository logRepository;
    @Autowired
    private ProjectRepository projectRepository;

    public ResLogDTO saveData(LogDTO dto) {
        Optional<UserEntity> userEntity = userRepository.findByApiKey(dto.getApiKey());
        Optional<Project> projectEntity = projectRepository.findByprojectName(dto.getProjectName());

        if (userEntity.isPresent() && projectEntity.isPresent()) {
            Log log = Log.builder()
                    .modelName(dto.getModelName())
                    .projectName(dto.getProjectName())
                    .inputData(dto.getInputData())
                    .outputData(dto.getOutputData())
                    .apiKey(dto.getApiKey())
                    .logDate(dto.getLogDate())
                    .isItEval("N")
                    .build();
            log = logRepository.save(log);

            return ResLogDTO.builder()
                    .modelName(log.getModelName())
                    .projectName(log.getProjectName())
                    .inputData(log.getInputData())
                    .outputData(log.getOutputData())
                    .logDate(log.getLogDate())
                    .id(log.getId())
                    .isItEval(log.getIsItEval())
                    .build();

        } else {
            throw new RuntimeException("존재하지 않는 API KEY 또는 프로젝트입니다.");
        }
    }
}
