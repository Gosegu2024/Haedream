package com.haedream.haedream.dto.response;

import java.time.LocalDateTime;

import com.haedream.haedream.entity.Log;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResLogDTO {
    // API 노출 X
    private String modelName;
    private String projectName;
    private String inputData;
    private String outputData;
    private LocalDateTime logDate;
    private String id;

    @Builder
    public ResLogDTO(
            String modelName,
            String projectName,
            String inputData,
            String outputData,
            LocalDateTime logDate,
            String id) {
        this.modelName = modelName;
        this.projectName = projectName;
        this.inputData = inputData;
        this.outputData = outputData;
        this.logDate = logDate;
        this.id = id;
    }

    // Entity를 DTO로 변환
    public static ResLogDTO fromEntity(Log log) {
        return ResLogDTO
                .builder()
                .modelName(log.getModelName())
                .projectName(log.getProjectName())
                .inputData(log.getInputData())
                .outputData(log.getOutputData())
                .logDate(log.getLogDate())
                .id(log.getId())
                .build();
    }
}
