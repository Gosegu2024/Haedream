package com.haedream.haedream.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.haedream.haedream.dto.request.EvalDTO;
import com.haedream.haedream.entity.Eval;
import com.haedream.haedream.service.EvalService;

import com.haedream.haedream.entity.Log;
import com.haedream.haedream.service.LoglistService;

import java.util.List;

@Controller
public class EvaluateController {

  @Autowired
  LoglistService loglistService;

  @GetMapping("/evaluate")
  public String evaluate(Model model) {
    List<Log> logList = loglistService.getLogList();
    model.addAttribute("logList", logList);
    return "evaluate";
  }

  @GetMapping("/evaluateLog")
  public String evaluateLog() {
    return "evaluateLog";
  }

  @PostMapping("/evaluateResult")
  public String evaluateResult(@RequestParam String result) {
    System.out.println(result);
    // Eval eval = evalService.saveEval(EvalDTO.parse(result));
    // System.out.println(eval.toString());

    return "evaluateResult";
  }

}
