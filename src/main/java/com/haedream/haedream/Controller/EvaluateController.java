package com.haedream.haedream.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EvaluateController {

  @GetMapping("/evaluate")
  public String evaluate() {
    return "evaluate";
  }

  @GetMapping("/evaluateLog")
  public String evaluateLog() {
    return "evaluateLog";
  }

  @GetMapping("/evaluateResult")
  public String evaluateResult() {
    return "evaluateResult";
  }

}
