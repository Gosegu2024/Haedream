package com.haedream.haedream.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "TB_LOG")
public class Log {

    @Id
    private String id;
    private String modelName;
    private String projectName;
    private String inputData;
    private String outputData;
    private String apiKey;
    private LocalDateTime logDate; // ZonedDateTime 사용
    private String formattedDate; // 포맷된 날짜 문자열을 저장할 필드 추가
    private String isItEval;

    @Builder
    public Log(String id, String modelName, String projectName, String inputData, String outputData, String apiKey,
    LocalDateTime logDate, String isItEval, String formattedDate) { // 생성자에 formattedDate 추가
        this.id = id;
        this.modelName = modelName;
        this.projectName = projectName;
        this.inputData = inputData;
        this.outputData = outputData;
        this.apiKey = apiKey;
        this.logDate = logDate;
        this.isItEval = isItEval;
        this.formattedDate = formattedDate; // 초기화
    }

}
