package com.haedream.haedream.dto.request;

import java.time.ZonedDateTime;
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
    private ZonedDateTime logDate;
    private String id;
    private String formattedDate; // 포맷된 날짜 문자열 추가

    public static LogDTO parse(String jsonStr) {
        Gson gson = new Gson();
        return gson.fromJson(jsonStr, LogDTO.class);
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
                .formattedDate(dto.getFormattedDate()) // 포맷된 날짜 설정
                .build();
    }
}
