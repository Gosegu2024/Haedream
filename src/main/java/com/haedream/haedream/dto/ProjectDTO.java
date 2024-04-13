package com.haedream.haedream.dto;

import java.time.ZonedDateTime;
import java.time.ZoneId;

public class ProjectDTO {
    private String id;
    private String projectName;
    private ZonedDateTime createDate; 
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

    public ZonedDateTime getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate.withZoneSameInstant(ZoneId.of("Asia/Seoul")); 
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

    public void setStandard(String standard) {
        this.standard = standard;
    }
}