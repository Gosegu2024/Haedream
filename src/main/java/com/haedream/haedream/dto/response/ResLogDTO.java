package com.haedream.haedream.dto.response;

import java.time.LocalDateTime;

import com.haedream.haedream.entity.Log;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResLogDTO {
    private String modelName;
    private String projectName;
    private String inputData;
    private String outputData;
    private LocalDateTime logDate;
    private String id;
    private String isItEval;
    private String formattedDate; // 포맷된 날짜 문자열 추가

    @Builder
    public ResLogDTO(
            String modelName,
            String projectName,
            String inputData,
            String outputData,
            LocalDateTime logDate,
            String id,
            String isItEval,
            String formattedDate) {
        this.modelName = modelName;
        this.projectName = projectName;
        this.inputData = inputData;
        this.outputData = outputData;
        this.logDate = logDate;
        this.id = id;
        this.isItEval = isItEval;
        this.formattedDate = formattedDate; // 초기화
    }

    public static ResLogDTO fromEntity(Log log) {
        return ResLogDTO.builder()
                .modelName(log.getModelName())
                .projectName(log.getProjectName())
                .inputData(log.getInputData())
                .outputData(log.getOutputData())
                .logDate(log.getLogDate())
                .id(log.getId())
                .isItEval(log.getIsItEval())
                .formattedDate(log.getFormattedDate()) // DTO 생성 시 포맷된 날짜도 설정
                .build();
    }
}
