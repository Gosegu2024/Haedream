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

    public Eval saveEval(EvalDTO evalDTO) { // 평가결과 저장

        // EvalDTO로부터 Eval 엔티티를 생성
        Eval eval = Eval.builder()
                .inputData(evalDTO.getInputData())
                .outputData(evalDTO.getOutputData())
                .username(evalDTO.getUsername())
                .logId(evalDTO.getLogId())
                .projectName(evalDTO.getProjectName())
                .evalLogDate(evalDTO.getEvalLogDate())
                .evalSummary(evalDTO.getEvalSummary())
                .evalTerminology(evalDTO.getEvalTerminology())
                .evalHallucination(evalDTO.getEvalHallucination())
                .evalReadability(evalDTO.getEvalReadability())
                .evalReadabilityScore(evalDTO.getEvalReadabilityScore())
                .evalPurpose(evalDTO.getEvalPurpose())
                .evalPurposeScore(evalDTO.getEvalPurposeScore())
                .evalProblem(evalDTO.getEvalProblem())
                .evalProblemScore(evalDTO.getEvalProblemScore())
                .evalCreative(evalDTO.getEvalCreative())
                .evalCreativeScore(evalDTO.getEvalCreativeScore())
                .evalContradiction(evalDTO.getEvalContradiction())
                .evalContradictionScore(evalDTO.getEvalContradictionScore())
                .HighLightContradiction(evalDTO.getHighLightContradiction())
                .evalStandard(evalDTO.getEvalStandard())
                .evalPrivacy(evalDTO.getEvalPrivacy())
                .HighLightPrivacy(evalDTO.getHighLightPrivacy())
                .evalFeedback(evalDTO.getEvalFeedback())
                .freqCnt(evalDTO.getFreqCnt())
                .tokenCnt(evalDTO.getTokenCnt())
                .letterCnt(evalDTO.getLetterCnt())
                .byteCnt(evalDTO.getByteCnt())
                .eng_list(evalDTO.getEng_list())
                .chi_list(evalDTO.getChi_list())
                .build();

        // MongoDB에 저장
        eval = evalRepository.save(eval);
        return eval;
    }
        
    // 로그 삭제
    public void deleteEvalsByLogId(String LogId) {       
    
        evalRepository.deleteEvalsByLogId(LogId);
    }    

}
