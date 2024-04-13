package com.haedream.haedream.dto.request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.haedream.haedream.config.LocalDateTimeAdapter;
import com.haedream.haedream.entity.Log;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


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
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        LogDTO dto = gson.fromJson(jsonStr, LogDTO.class);
        if (dto.getLogDate() == null) {
            dto.setLogDate(LocalDateTime.now());
        }
        return dto;
    }

    public static Log ofEntity(LogDTO dto) {
        return Log.builder()
                .apiKey(dto.getApiKey())
                .inputData(dto.getInputData())
                .outputData(dto.getOutputData())
                .projectName(dto.getProjectName())
                .modelName(dto.getModelName())
                .logDate(dto.getLogDate())
                .id(dto.getId())
                .isItEval("N")
                .build();
    }
}
