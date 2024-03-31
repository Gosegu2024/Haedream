package com.haedream.haedream.dto.request;

public class ProjectIdRequest {
  private String projectId;

  public ProjectIdRequest() {
  }

  public ProjectIdRequest(String projectId) {
    this.projectId = projectId;
  }

  public String getProjectId() {
    return projectId;
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }
}
