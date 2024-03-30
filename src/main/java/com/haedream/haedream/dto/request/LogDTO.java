package com.haedream.haedream.dto.request;

import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.haedream.haedream.config.LocalDateTimeAdapter;
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
    private LocalDateTime logDate; // logDate : 생성된 시간
    private String id; // 몽고디비에서 자동 생성될 id(고유값)필드

    public static LogDTO parse(String jsonStr) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        LogDTO dto = gson.fromJson(jsonStr, LogDTO.class);
        if (dto.getLogDate() == null) {
            dto.setLogDate(LocalDateTime.now());
        }
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
