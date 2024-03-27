package com.haedream.haedream.entity;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Log {
    private String modelName;
    private String projectName;
    private Map<String, Object> inputData;
    private String outputData;
    private String apiKey;

    @Builder
    public Log(String modelName, String projectName, Map<String, Object> inputData, String outputData, String apiKey) {
        // System.out.println("Log");
        // System.out.println(modelName);
        this.modelName = modelName;
        this.projectName = projectName;
        this.inputData = inputData;
        this.outputData = outputData;
        this.apiKey = apiKey;
    }
}
