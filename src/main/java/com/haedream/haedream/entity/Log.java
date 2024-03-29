package com.haedream.haedream.entity;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "TB_LOG")
public class Log {
    private String modelName;
    private String projectName;
    private String inputData;
    private String outputData;
    private String apiKey;
    private LocalDateTime logDate;
    private String id;

    @Builder
    public Log(
            String modelName,
            String projectName,
            String inputData,
            String outputData,
            String apiKey,
            LocalDateTime logDate,
            String id) {
        this.modelName = modelName;
        this.projectName = projectName;
        this.inputData = inputData;
        this.outputData = outputData;
        this.apiKey = apiKey;
        this.logDate = logDate;
        this.id = id;
    }
}
