package com.haedream.haedream.service;

import com.haedream.haedream.dto.request.EvalDTO;
import com.haedream.haedream.entity.Eval;
import com.haedream.haedream.repository.EvalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvalService {

    @Autowired
    private EvalRepository evalRepository;

    @SuppressWarnings("null")
    public Eval saveEval(EvalDTO evalDTO) { // 평가결과 저장
        // EvalDTO로부터 Eval 엔티티를 생성
        Eval eval = Eval.builder()
                        .standard(evalDTO.getStandard())
                        .apiKey(evalDTO.getApiKey())
                        .evalFeedback(evalDTO.getEvalFeedback())
                        .evalLogDate(evalDTO.getEvalLogDate())
                        .evalWhether(evalDTO.getEvalWhether())
                        .inputData(evalDTO.getLog().getInputData())
                        .outputData(evalDTO.getLog().getOutputData())
                        .build();

        // MongoDB에 저장
        eval = evalRepository.save(eval);
        return eval;
    }
}
