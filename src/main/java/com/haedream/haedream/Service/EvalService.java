package com.haedream.haedream.service;

import com.haedream.haedream.dto.ListDTO;
import com.haedream.haedream.dto.request.EvalDTO;
import com.haedream.haedream.entity.Eval;
import com.haedream.haedream.entity.Log;
import com.haedream.haedream.repository.EvalRepository;
import com.haedream.haedream.util.DateUtils;

import java.util.ArrayList;
import java.util.List;

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
                .formattedevalLogDate(evalDTO.getFormattedevalLogDate())
                .build();

        // MongoDB에 저장
        eval = evalRepository.save(eval);
        return eval;
    }

    // 로그 삭제
    public void deleteEvalsByLogId(String LogId) {

        evalRepository.deleteEvalsByLogId(LogId);
    }

    // 로그 아이디로 eval 조회
    public Eval getEvalByLogIdAndUsernameAndProjectName(String logid, String username, String projectName) {
        Eval eval = evalRepository.findOneByLogIdAndUsernameAndProjectName(logid, username, projectName);
        return eval;
    }

    public List<Eval> getEvalList(List<Log> logList) {
        List<Eval> evalList = new ArrayList<>();
        for (Log log : logList) {
            String logId = log.getId();
            Eval eval = evalRepository.findOneByLogId(logId);
            evalList.add(eval);
        }
        return evalList;
    }

    public String[] eng_list(String eng_list) {
        String[] engList = eng_list.replaceAll("\\[|\\]|'", "").split(",\\s*");
        return engList;
    }

    public List<String[]> freqCnt(String freqCnt) {
        List<String[]> freqCntList = new ArrayList<>();
        String[] items = freqCnt.split("\\), \\(");
        for (String item : items) {
            item = item.replaceAll("\\[|\\]|'|\\(|\\)", "");
            String[] pair = item.split(", ");
            freqCntList.add(pair);
        }
        return freqCntList;
    }

    public List<ListDTO> getListDTOList(List<Eval> evalList, List<Log> logList) {
        ListDTO dto = new ListDTO();
        List<ListDTO> ListDTOList = new ArrayList<>();

        for (Log log : logList) {
            dto = new ListDTO();
            for (Eval eval : evalList) {
                if (log.getId().equals(eval.getLogId())) {
                    String formattedDate = DateUtils.formatZonedDateTime(eval.getEvalLogDate());
                    dto.setModelName(log.getModelName());
                    dto.setLogId(eval.getLogId());
                    dto.setFormattedevalLogDate(formattedDate);
                    dto.setProjectName(eval.getProjectName());
                }
            }
            ListDTOList.add(dto);
        }

        return ListDTOList;
    }

}
