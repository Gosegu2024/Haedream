package com.haedream.haedream.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.haedream.haedream.dto.request.EvalDTO;
import com.haedream.haedream.entity.Eval;
import com.haedream.haedream.entity.Log;
import com.haedream.haedream.entity.UserEntity;
import com.haedream.haedream.repository.EvalRepository;
import com.haedream.haedream.repository.LogRepository;
import com.haedream.haedream.repository.ProjectRepository;
import com.haedream.haedream.repository.UserRepository;
import com.haedream.haedream.service.EvalService;
import com.haedream.haedream.service.LoglistService;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private EvalRepository evalRepository;

  @GetMapping("/evaluateSelect")
  public String evaluatsSelect(@RequestParam("projectName") String projectName, HttpSession session) {
    session.setAttribute("projectName", projectName);
    return "redirect:/evaluate";
  }

  // 평가하기 가져오기
  @GetMapping("/evaluate")
  public String evaluate(HttpSession session, Model model) {
    String projectName = (String) session.getAttribute("projectName");
    String username = (String) session.getAttribute("username");
    UserEntity user = userRepository.findByUsername(username);
    String apiKey = user.getApi_key();

    // logListService 호출
    List<Log> logList = loglistService.getLogList(apiKey, projectName);

    // 모델에 값 추가
    model.addAttribute("logList", logList);

    return "evaluate";
  }

  // 평가하기 삭제
  @PostMapping("/evaluate/delete")
  public ResponseEntity<Map<String, String>> evaluatedelete(@RequestBody Map<String, String> requestMap) {
    Map<String, String> response = new HashMap<>();
    try {

      String apiKey = requestMap.get("apiKey");
      String projectName = requestMap.get("projectName");
      String id = requestMap.get("id");

      loglistService.deleteLogsByApiKeyAndProjectNameAndId(apiKey, projectName, id);

      response.put("message", "Log deleted successfully");
      return ResponseEntity.ok().body(response);
    } catch (Exception e) {
      response.put("error", "Failed to delete log: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }

  // 평가기록 가져오기
  @GetMapping("/evaluateLog")
  public String evaluateLog(HttpSession session, Model model) {
    String username = (String) session.getAttribute("username");
    UserEntity user = userRepository.findByUsername(username);
    String apiKey = user.getApi_key();
    String projectName = (String) session.getAttribute("projectName");

    List<Log> logList = logRepository.findByApiKeyAndProjectNameAndIsItEval(apiKey, projectName, "Y");

    List<Eval> evalList = new ArrayList<>();
    for (Log log : logList) {
      String logId = log.getId();
      Eval eval = evalRepository.findOneByLogId(logId);
      evalList.add(eval);
    }

    // 모델에 값 추가
    model.addAttribute("evalList", evalList);

    return "evaluateLog";
  }

  // 평가 결과 다시보기
  @GetMapping("/evaluateResultCheck")
  public String evaluateResultCheck(@RequestParam("logId") String logid, HttpSession session, Model model) {
    String username = (String) session.getAttribute("username");
    String projectName = (String) session.getAttribute("projectName");

    Eval evalDTO = evalService.getEvalByLogIdAndUsernameAndProjectName(logid, username, projectName);
    String outputdata = evalDTO.getOutputData();
    String eng_list = evalDTO.getEng_list();

    // 문자열에서 필요한 부분만 추출하여 처리
    String[] engList = eng_list.replaceAll("\\[|\\]|'", "").split(",\\s*");
    
    model.addAttribute("evalDTO", evalDTO);
    model.addAttribute("outputdata", outputdata);
    model.addAttribute("eng_list", engList);

    return "evaluateResultCheck";
  }

  // 평가기록 삭제
  @PostMapping("/evaluateLog/delete")
  public ResponseEntity<Map<String, String>> evaluateLogDelete(@RequestBody Map<String, String> requestMap) {
    Map<String, String> response = new HashMap<>();
    try {
      String logId = requestMap.get("Id");

      evalRepository.deleteByLogId(logId);

      Log log = logRepository.findById(logId).orElse(null);

      if (log != null) {
        log.setIsItEval("N");
        logRepository.save(log);
      }
      response.put("message", "Evaluation deleted successfully");
      return ResponseEntity.ok().body(response);
    } catch (Exception e) {
      response.put("error", "Failed to delete evaluation: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }

  // 평가 실행
  @GetMapping("/evaluateResult")
  public String evaluateResult(@RequestParam("logId") String logId,
      Model model,
      HttpSession session) {

    session.setAttribute("logId", logId);

    String[] result = getLog(session);
    String outputdata = result[0];
    String standard = result[1];
    String inputdata = result[2];
    model.addAttribute("inputdata", inputdata);

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
  public ResponseEntity<?> sendValues(@RequestParam("outputdata") String outputdata,
      @RequestParam("inputdata") String inputdata,
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
        model.addAttribute("inputdata", inputdata);
        model.addAttribute("outputdata", outputdata);
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

    Eval saveEvalDTO = evalService.saveEval(EvalDTO.parse(evalresult));

    String logId = saveEvalDTO.getLogId();
    // 평가여부 업데이트
    Log log = logRepository.findById(logId).get();
    log.setIsItEval("Y");
    logRepository.save(log);

    String username = saveEvalDTO.getUsername();
    session.setAttribute("username", username);
    String projectName = saveEvalDTO.getProjectName();
    session.setAttribute("projectName", projectName);

    // return ResponseEntity.status(HttpStatus.CREATED).body(saveEvalDTO);

    return null;
  }

}