package com.haedream.haedream.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "TB_EVALUATION")
public class Eval {

    @Id
    private String id;
    private String inputData;
    private String outputData;
    private String username;
    private String logId;
    private String projectName;
    private LocalDateTime evalLogDate;
    private String evalSummary;
    private String evalTerminology;
    private String evalHallucination;
    private String evalReadability;
    private String evalReadabilityScore;
    private String evalPurpose;
    private String evalPurposeScore;
    private String evalProblem;
    private String evalProblemScore;
    private String evalCreative;
    private String evalCreativeScore;
    private String evalContradiction;
    private String evalContradictionScore;
    private String HighLightContradiction;
    private String evalStandard;
    private String evalPrivacy;
    private String HighLightPrivacy;
    private String evalFeedback;
    private String freqCnt;
    private int tokenCnt;
    private int letterCnt;
    private int byteCnt;
    private String eng_list;
    private String chi_list;
    private String formattedevalLogDate;

    @Builder
    public Eval(String id, String inputData, String outputData, String username, String logId, String projectName,
    LocalDateTime evalLogDate, String evalSummary, String evalTerminology, String evalHallucination,
            String evalReadability, String evalReadabilityScore, String evalPurpose, String evalPurposeScore,
            String evalProblem, String evalProblemScore, String evalCreative, String evalCreativeScore,
            String evalContradiction, String evalContradictionScore, String HighLightContradiction, String evalStandard,
            String evalPrivacy, String HighLightPrivacy, String evalFeedback, String freqCnt, int tokenCnt,
            int letterCnt, int byteCnt, String eng_list, String chi_list, String formattedevalLogDate) {
        this.id = id;
        this.inputData = inputData;
        this.outputData = outputData;
        this.username = username;
        this.logId = logId;
        this.projectName = projectName;
        this.evalLogDate = evalLogDate;
        this.evalSummary = evalSummary;
        this.evalTerminology = evalTerminology;
        this.evalHallucination = evalHallucination;
        this.evalReadability = evalReadability;
        this.evalReadabilityScore = evalReadabilityScore;
        this.evalPurpose = evalPurpose;
        this.evalPurposeScore = evalPurposeScore;
        this.evalProblem = evalProblem;
        this.evalProblemScore = evalProblemScore;
        this.evalCreative = evalCreative;
        this.evalCreativeScore = evalCreativeScore;
        this.evalContradiction = evalContradiction;
        this.evalContradictionScore = evalContradictionScore;
        this.HighLightContradiction = HighLightContradiction;
        this.evalStandard = evalStandard;
        this.evalPrivacy = evalPrivacy;
        this.HighLightPrivacy = HighLightPrivacy;
        this.evalFeedback = evalFeedback;
        this.freqCnt = freqCnt;
        this.tokenCnt = tokenCnt;
        this.letterCnt = letterCnt;
        this.byteCnt = byteCnt;
        this.eng_list = eng_list;
        this.chi_list = chi_list;
        this.formattedevalLogDate = formattedevalLogDate;
    }

    public Eval() {
    }

}
