package com.haedream.haedream.service;

import com.haedream.haedream.dto.EvalDTO;
import com.haedream.haedream.entity.Eval;
import com.haedream.haedream.repository.EvalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvalService {

    private final EvalRepository evalRepository;

    @Autowired
    public EvalService(EvalRepository evalRepository) {
        this.evalRepository = evalRepository;
    }

    public Eval saveEval(EvalDTO evalDTO) { // 평가결과 저장
        // EvalDTO로부터 Eval 엔티티를 생성
        Eval eval = new Eval();
        eval.setStandard(evalDTO.getStandard());

        eval.setInputData(evalDTO.getLog().getInputData());
        eval.setOutputData(evalDTO.getLog().getOutputData());
        eval.setApiKey(evalDTO.getApiKey());
        eval.setEvalLogDate(evalDTO.getEvalLogDate());
        eval.setEvalWhether(evalDTO.getEvalWhether());
        eval.setEvalFeedback(evalDTO.getEvalFeedback());

        // MongoDB에 저장
        return evalRepository.save(eval);
    }
}
