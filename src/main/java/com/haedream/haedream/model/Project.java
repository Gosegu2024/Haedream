package com.haedream.haedream.model;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "TB_PROJECT")
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    @Id
    private String id;
    private String projectName;
    private String standard;
    private ZonedDateTime createDate; 
    private String username;
    private String owner;

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

    public String getStandard() {
        return this.standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}