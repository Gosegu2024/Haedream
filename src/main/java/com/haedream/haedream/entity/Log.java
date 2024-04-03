package com.haedream.haedream.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
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

    @Id
    private String id;
    private String modelName;
    private String projectName;
    private String inputData;
    private String outputData;
    private String apiKey;
    private LocalDateTime logDate;

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

    /**
     * @return String return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return String return the modelName
     */
    public String getModelName() {
        return modelName;
    }

    /**
     * @param modelName the modelName to set
     */
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    /**
     * @return String return the projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @param projectName the projectName to set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * @return String return the inputData
     */
    public String getInputData() {
        return inputData;
    }

    /**
     * @param inputData the inputData to set
     */
    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    /**
     * @return String return the outputData
     */
    public String getOutputData() {
        return outputData;
    }

    /**
     * @param outputData the outputData to set
     */
    public void setOutputData(String outputData) {
        this.outputData = outputData;
    }

    /**
     * @return String return the apiKey
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * @param apiKey the apiKey to set
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * @return LocalDateTime return the logDate
     */
    public LocalDateTime getLogDate() {
        return logDate;
    }

    /**
     * @param logDate the logDate to set
     */
    public void setLogDate(LocalDateTime logDate) {
        this.logDate = logDate;
    }

}
