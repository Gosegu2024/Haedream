package com.haedream.haedream.dto.request;

import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.haedream.haedream.config.LocalDateTimeAdapter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EvalDTO {
    private String inputData;
    private String outputData;
    private String logId;
    private String username;
    private String projectName;
    private LocalDateTime evalLogDate; // 평가 일시
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

    // JSON 문자열로부터 EvalDTO 객체를 파싱하는 정적 메소드
    public static EvalDTO parse(String result) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        EvalDTO dto = gson.fromJson(result, EvalDTO.class);

        return dto;
    }
}
