package com.haedream.haedream.dto.response;

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

    @Builder
    public ResLogDTO(String modelName, String projectName, String inputData, String outputData) {
        this.modelName = modelName;
        this.projectName = projectName;
        this.inputData = inputData;
        this.outputData = outputData;
    }

    // Entity를 DTO로 변환
    public static ResLogDTO fromEntity(Log log) {
        // System.out.println("ResLogDTO");
        // System.out.println(log.getModelName());
        return ResLogDTO.builder()
                .modelName(log.getModelName())
                .projectName(log.getProjectName())
                .inputData(log.getInputData())
                .outputData(log.getOutputData())
                .build();
    }
}
