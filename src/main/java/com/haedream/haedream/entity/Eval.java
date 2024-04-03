package com.haedream.haedream.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;

import java.time.LocalDateTime;

@Document(collection = "TB_EVALUATION")
public class Eval {

    @Id
    private String id;
    private String standard;
    private String inputData;
    private String outputData;
    private String apiKey;
    private LocalDateTime evalLogDate;
    private String evalWhether;
    private String evalFeedback;

    @Builder
    public Eval(String id, String standard, String inputData, String outputData, String apiKey, LocalDateTime evalLogDate, String evalWhether, String evalFeedback) {
        this.id = id;
        this.standard = standard;
        this.inputData = inputData;
        this.outputData = outputData;
        this.apiKey = apiKey;
        this.evalLogDate = evalLogDate;
        this.evalWhether = evalWhether;
        this.evalFeedback = evalFeedback;
    }

    public Eval() {
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public String getOutputData() {
        return outputData;
    }

    public void setOutputData(String outputData) {
        this.outputData = outputData;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public LocalDateTime getEvalLogDate() {
        return evalLogDate;
    }

    public void setEvalLogDate(LocalDateTime evalLogDate) {
        this.evalLogDate = evalLogDate;
    }

    public String getEvalWhether() {
        return evalWhether;
    }

    public void setEvalWhether(String evalWhether) {
        this.evalWhether = evalWhether;
    }

    public String getEvalFeedback() {
        return evalFeedback;
    }

    public void setEvalFeedback(String evalFeedback) {
        this.evalFeedback = evalFeedback;
    }
}
