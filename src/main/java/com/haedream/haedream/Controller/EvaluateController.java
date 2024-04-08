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
import com.haedream.haedream.repository.LogRepository;
import com.haedream.haedream.repository.ProjectRepository;
import com.haedream.haedream.service.EvalService;
import com.haedream.haedream.service.LoglistService;

import jakarta.servlet.http.HttpSession;

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

  @GetMapping("/evaluate")
  public String evaluate(@RequestParam(value = "projectName", required = false) String projectName, @RequestParam(value = "apiKey", required = false) String apiKey, HttpSession session,
                        Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if (projectName == null || apiKey == null) {
            projectName = (String) session.getAttribute("projectName");
            apiKey = (String) session.getAttribute("apiKey");
        } else {
            session.setAttribute("projectName", projectName);
            session.setAttribute("apiKey", apiKey);
        }

        session.setAttribute("username", username);

    String sessionUserName = (String) session.getAttribute("username");
    String sessionApiKey = (String) session.getAttribute("apiKey");
    String sessionProjectName = (String) session.getAttribute("projectName");

    // logListService 호출
    List<Log> logList = loglistService.getLogList(sessionApiKey, sessionProjectName);

    // 모델에 값 추가
    model.addAttribute("username", sessionUserName);
    model.addAttribute("logList", logList);

    return "evaluate";
  }

  // 평가 삭제
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

  @GetMapping("/evaluateLog")
  public String evaluateLog() {
    return "evaluateLog";
  }

  // 평가 실행
  @GetMapping("/evaluateResult")
  public String evaluateResult(@RequestParam("logId") String logId, @RequestParam("projectName") String projectName,
      Model model) {
    String[] result = getLog(logId, projectName, model);
    String outputdata = result[0];
    String standard = result[1];
    sendValues(outputdata, standard, model);

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    model.addAttribute("projectName", projectName);
    model.addAttribute("username", username);
    return "evaluateResult";
  }

  // 평가용 로그 조회
  @SuppressWarnings("null")
  public String[] getLog(String logId, String projectName, Model model) {
    Log log_info = logRepository.findById(logId).get();
    model.addAttribute("inputdata", log_info.getInputData());
    String outputdata = log_info.getOutputData();
    String standard = projectRepository.findByProjectName(projectName).get(0).getStandard();
    String[] result = new String[2];
    result[0] = outputdata;
    result[1] = standard;

    return result;
  }

  // 평가 모델 실행
  @PostMapping("/sendValues")
  public ResponseEntity<?> sendValues(@RequestParam("outputdata") String outputdata,
      @RequestParam("standard") String standard, Model model) {
    System.out.println("평가시작");
    String url = "http://localhost:8008/evaluate";

    // 요청 본문에 데이터를 담기
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode requestBody = objectMapper.createObjectNode();
    requestBody.put("outputdata", outputdata);
    requestBody.put("standard", standard);

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
  public ResponseEntity<Eval> saveEval(@RequestParam String evalresult) {
    Eval saveEvalDTO = evalService.saveEval(EvalDTO.parse(evalresult));

    return ResponseEntity.status(HttpStatus.CREATED).body(saveEvalDTO);
  }

}
