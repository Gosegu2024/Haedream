package com.haedream.haedream.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.haedream.haedream.dto.request.ProjectIdRequest;
import com.haedream.haedream.service.ProjectService;

@RestController
@RequestMapping("/main")
public class ProjectController {

  @Autowired
  private ProjectService projectService;

  @PostMapping("/projectDelete")
  public ResponseEntity<List<String>> projectDelete(@RequestBody List<String> projectIds) {
    projectService.deleteProjects(projectIds);
    return ResponseEntity.ok().body(projectIds);
  }

  @PostMapping("/getStandard")
  @ResponseBody
  public String getStandard(@RequestBody ProjectIdRequest request) {
    String projectId = request.getProjectId();
    String standard = projectService.getStandard(projectId);
    return standard;
  }
}