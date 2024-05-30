package com.haedream.haedream.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.haedream.haedream.dto.ListDTO;
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

  @GetMapping("/goEvaluateResult")
  public String goEvaluateResult(@RequestParam("logId") String logId, HttpSession session) {
    session.setAttribute("logId", logId);
    return "redirect:/evaluateResult";
  }

  @GetMapping("/goEvaluateResultCheck")
  public String goEvaluateResultCheck(@RequestParam("logId") String logId, HttpSession session) {
    session.setAttribute("logId", logId);
    return "redirect:/evaluateResultCheck";
  }

  @GetMapping("/evaluate")
  public String evaluate(HttpSession session, Model model) {
    String projectName = (String) session.getAttribute("projectName");
    String username = (String) session.getAttribute("username");

    if (projectName == null || username == null) {
      return "redirect:/main";
    }
    UserEntity user = userRepository.findByUsername(username);
    String apiKey = user.getApi_key();

    List<Log> logList = loglistService.getLogList(apiKey, projectName);

    List<Map<String, String>> formattedLogs = loglistService.logListFormat(logList);
    
    model.addAttribute("formattedLogs", formattedLogs);

    return "evaluate";
  }

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
  public String evaluateLog(HttpSession session, Model model) {
    String username = (String) session.getAttribute("username");
    UserEntity user = userRepository.findByUsername(username);
    String apiKey = user.getApi_key();
    String projectName = (String) session.getAttribute("projectName");

    if (projectName == null || username == null) {
      return "redirect:/main";
    }

    List<Log> logList = loglistService.getLogListEvaluated(apiKey, projectName);
    List<Eval> evalList = evalService.getEvalList(logList);

    List<ListDTO> listDTOList = evalService.getListDTOList(evalList, logList);

    model.addAttribute("dtoList", listDTOList);

    return "evaluateLog";
  }

  @GetMapping("/evaluateResultCheck")
  public String evaluateResultCheck(HttpSession session, Model model) {
    String username = (String) session.getAttribute("username");
    String projectName = (String) session.getAttribute("projectName");
    String logId = (String) session.getAttribute("logId");

    Eval evalDTO = evalService.getEvalByLogIdAndUsernameAndProjectName(logId, username, projectName);
    String outputdata = evalDTO.getOutputData();
    String eng_list = evalDTO.getEng_list();
    String freqCnt = evalDTO.getFreqCnt();

    List<String> engList = evalService.eng_list(eng_list);
    List<String[]> freqCntList = evalService.freqCnt(freqCnt);
    String output = evalService.replaceOutput(outputdata);

    model.addAttribute("evalDTO", evalDTO);
    model.addAttribute("outputdata", output);
    model.addAttribute("eng_list", engList);
    model.addAttribute("freqCnt", freqCntList);

    return "evaluateResultCheck";
  }

  @PostMapping("/evaluateLog/delete")
  public ResponseEntity<Map<String, String>> evaluateLogDelete(@RequestBody Map<String, String> requestMap) {
    Map<String, String> response = new HashMap<>();
    try {
      String logId = requestMap.get("Id");
      
      evalRepository.deleteByLogId(logId);

      loglistService.updateIsItEvalN(logId);

      response.put("message", "Evaluation deleted successfully");
      return ResponseEntity.ok().body(response);
    } catch (Exception e) {
      response.put("error", "Failed to delete evaluation: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }

  @GetMapping("/evaluateResult")
  public String evaluateResult(Model model, HttpSession session) {
    String[] result = getLog(session);
    String outputdata = result[0];
    String standard = result[1];
    String inputdata = result[2];
    model.addAttribute("inputdata", inputdata);

    sendValues(outputdata, inputdata, standard, model, session);
    return "evaluateResult";
  }

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

  @PostMapping("/sendValues")
  @CrossOrigin("https://port-0-haedream-python-ss7z32llwpyqpf9.sel5.cloudtype.app")
  public ResponseEntity<?> sendValues(@RequestParam("outputdata") String outputdata,
      @RequestParam("inputdata") String inputdata,
      @RequestParam("standard") String standard, Model model, HttpSession session) {
    String url = "https://port-0-haedream-python-ss7z32llwpyqpf9.sel5.cloudtype.app/evaluate";

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

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<ObjectNode> requestEntity = new HttpEntity<>(requestBody, headers);
    ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

    if (responseEntity.getStatusCode().is2xxSuccessful()) {
      String response = responseEntity.getBody();
      try {
        List<Object> responseList = objectMapper.readValue(response, new TypeReference<List<Object>>() {
        });
        String output = evalService.replaceOutput(outputdata);
        List<String> eng_list = evalService.eng_list2(responseList.get(5).toString());
        model.addAttribute("eng_list", eng_list);
        model.addAttribute("inputdata", inputdata);
        model.addAttribute("outputdata", output);
        model.addAttribute("responseList", responseList);
      } catch (JsonProcessingException e) {
        System.err.println("Failed to parse response from server: " + e.getMessage());
      }

      return responseEntity;

    } else {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to receive response from server.");
    }
  }

  @PostMapping("/save_eval")
  public ResponseEntity<Eval> saveEval(@RequestParam String evalresult, HttpSession session) {

    Eval saveEvalDTO = evalService.saveEval(EvalDTO.parse(evalresult));

    String logId = saveEvalDTO.getLogId();
    loglistService.updateIsItEvalY(logId);

    String username = saveEvalDTO.getUsername();
    session.setAttribute("username", username);
    String projectName = saveEvalDTO.getProjectName();
    session.setAttribute("projectName", projectName);

    return null;
  }

}