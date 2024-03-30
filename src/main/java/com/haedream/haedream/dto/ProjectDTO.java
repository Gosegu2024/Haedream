package com.haedream.haedream.dto;

import java.time.LocalDateTime;

public class ProjectDTO {
    private String id;
    private String projectName;
    private LocalDateTime createDate;
    private String username;
    private String standard;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public LocalDateTime getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStandard() {
        return this.standard;
    }

    public void setEvalStandard(String standard) {
        this.standard = standard;
    }

}
