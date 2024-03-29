package com.haedream.haedream.dto.request;

import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.haedream.haedream.entity.Log;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LogDTO {
    private String modelName;
    private String projectName;
    private String inputData;
    private String outputData;
    private String apiKey;
    private LocalDateTime logDate;
    private String id;

    public static LogDTO parse(String jsonStr) {
        Gson gson = new Gson();
        LogDTO dto = gson.fromJson(jsonStr, LogDTO.class);

        return dto;
    }

    // DTO를 Entity로 변환
    public static Log ofEntity(LogDTO dto) {
        // System.out.println("LogDTO"); System.out.println(dto.getApiKey());
        return Log
                .builder()
                .apiKey(dto.getApiKey())
                .inputData(dto.getInputData())
                .outputData(dto.getOutputData())
                .projectName(dto.getProjectName())
                .modelName(dto.getModelName())
                .logDate(dto.getLogDate())
                .id(dto.getId())
                .build();
    }
}
