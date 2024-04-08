package com.haedream.haedream.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.haedream.haedream.dto.request.EvalDTO;
import com.haedream.haedream.entity.Eval;
import com.haedream.haedream.entity.Log;
import com.haedream.haedream.repository.LogRepository;
import com.haedream.haedream.repository.ProjectRepository;
import com.haedream.haedream.service.EvalService;
import com.haedream.haedream.service.LoglistService;

import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
public class EvaluateController {

  @Autowired
  RestTemplate restTemplate;

  @Autowired
  LoglistService loglistService;

  @Autowired
  private LogRepository logRepository;

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private EvalService evalService;

  @GetMapping("/evaluate")
  public String evaluate(@RequestParam("projectName") String projectName, @RequestParam("apiKey") String apiKey,
      Model model, HttpSession session) {
    List<Log> logList = loglistService.getLogList(apiKey, projectName);
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    model.addAttribute("username", username);
    model.addAttribute("logList", logList);

    session.setAttribute("projectName", projectName);
    return "evaluate";
  }

  @GetMapping("/evaluateLog")
  public String evaluateLog() {
    return "evaluateLog";
  }

  // 평가 실행
  @GetMapping("/evaluateResult")
  public String evaluateResult(@RequestParam("logId") String logId, @RequestParam("projectName") String projectName, Model model,
     HttpSession session) {
    session.setAttribute("logId", logId);

    String[] result = getLog(session);
    String outputdata = result[0];
    String standard = result[1];
    String inputdata = result[2];

    sendValues(outputdata, inputdata, standard, model, session);

    return "evaluateResult";
  }

  // 평가용 로그 조회
  public String[] getLog(HttpSession session) {
    String logId = (String) session.getAttribute("logId");
    String username = (String) session.getAttribute("username");
    String projectName = (String) session.getAttribute("projectName");

    Log log_info = logRepository.findById(logId).get();
    
    String inputdata = log_info.getInputData();
    String outputdata = log_info.getOutputData();
    String standard = projectRepository.findByProjectNameAndOwner(projectName, username).get(0).getStandard();

    String[] result = new String[3];
    result[0] = outputdata;
    result[1] = standard;
    result[2] = inputdata;

    return result;
  }

  // 평가 모델 실행
  @PostMapping("/sendValues")
  public ResponseEntity<?> sendValues(@RequestParam("outputdata") String outputdata, @RequestParam("inputdata") String inputdata,
      @RequestParam("standard") String standard, Model model, HttpSession session) {
    System.out.println("평가시작");
    String url = "http://localhost:8008/evaluate";

    // 요청 본문에 데이터를 담기
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode requestBody = objectMapper.createObjectNode();
    String username = (String) session.getAttribute("username");
    String projectName = (String) session.getAttribute("projectName");
    String logId = (String) session.getAttribute("logId");

    requestBody.put("inputData", inputdata);
    requestBody.put("outputData", outputdata);
    requestBody.put("standard", standard);
    requestBody.put("username", username);
    requestBody.put("projectName", projectName);
    requestBody.put("logId", logId);

    // HTTP 요청 헤더 설정
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    // HTTP 요청 보내기
    HttpEntity<ObjectNode> requestEntity = new HttpEntity<>(requestBody, headers);
    ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

    // 반환된 응답 출력
    if (responseEntity.getStatusCode().is2xxSuccessful()) {
      String response = responseEntity.getBody();
      try {
        // JSON 배열을 읽기 위해 ObjectMapper를 사용하여 리스트로 변환
        List<Object> responseList = objectMapper.readValue(response, new TypeReference<List<Object>>() {
        });
        model.addAttribute("responseList", responseList);
      } catch (JsonProcessingException e) {
        System.err.println("Failed to parse response from server: " + e.getMessage());
      }

      return responseEntity;

    } else {
      System.out.println("Failed to receive response from server. Status code: " + responseEntity.getStatusCode());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to receive response from server.");
    }
  }

  // 평가결과 DB 저장
  @PostMapping("/save_eval")
  public ResponseEntity<Eval> saveEval(@RequestParam String evalresult, HttpSession session) {

    // System.out.println(evalresult);

    Eval saveEvalDTO = evalService.saveEval(EvalDTO.parse(evalresult));

    System.out.println("내용 확인");
    System.out.println("1 "+saveEvalDTO.getEvalSummary()+"\n\n");
    System.out.println("2 "+saveEvalDTO.getEvalTerminology()+"\n\n"); 
    System.out.println("3 "+saveEvalDTO.getEvalHallucination()+"\n\n");
    System.out.println("4 "+saveEvalDTO.getEvalReadability()+"\n\n");
    System.out.println("5 "+saveEvalDTO.getEvalReadabilityScore()+"\n\n");
    System.out.println("6 "+saveEvalDTO.getEvalPurpose()+"\n\n");
    System.out.println("7 "+saveEvalDTO.getEvalPurposeScore()+"\n\n");
    System.out.println("8 "+saveEvalDTO.getEvalProblem()+"\n\n");
    System.out.println("9 "+saveEvalDTO.getEvalProblemScore()+"\n\n");
    System.out.println("10 "+saveEvalDTO.getEvalCreative()+"\n\n");
    System.out.println("11 "+saveEvalDTO.getEvalCreativeScore()+"\n\n");
    System.out.println("12 "+saveEvalDTO.getEvalContradiction()+"\n\n");
    System.out.println("13 "+saveEvalDTO.getEvalContradictionScore()+"\n\n"); //
    System.out.println("14 "+saveEvalDTO.getHighLightContradiction()+"\n\n"); //
    System.out.println("15 "+saveEvalDTO.getEvalStandard()+"\n\n"); //
    System.out.println("16 "+saveEvalDTO.getEvalPrivacy()+"\n\n"); //
    System.out.println("17 "+saveEvalDTO.getHighLightPrivacy()+"\n\n"); //
    System.out.println("18 "+saveEvalDTO.getEvalFeedback()+"\n\n"); //


    String username = saveEvalDTO.getUsername();
    session.setAttribute("username", username);
    String projectName = saveEvalDTO.getProjectName();
    session.setAttribute("projectName", projectName);

    // return ResponseEntity.status(HttpStatus.CREATED).body(saveEvalDTO);
    return null;
  }

}
