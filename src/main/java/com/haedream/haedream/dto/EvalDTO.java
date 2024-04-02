package com.haedream.haedream.dto;

import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.haedream.haedream.config.LocalDateTimeAdapter;
import com.haedream.haedream.dto.request.LogDTO;
import com.haedream.haedream.entity.Log;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EvalDTO {
    private String evalId; // MongoDB에서 자동으로 생성될 식별자
    private String standard; // 평가 기준
    private LogDTO log; // 로그 데이터 (id, inputData, outputData 포함)
    private String apiKey; // 회원 apiKey
    private LocalDateTime evalLogDate; // 평가 일시
    private String evalWhether; // 평가 여부
    private String evalFeedback; // 평가 피드백

    // JSON 문자열로부터 EvalDTO 객체를 파싱하는 정적 메소드
    public static EvalDTO parse(String jsonStr) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        return gson.fromJson(jsonStr, EvalDTO.class);
    }
}
