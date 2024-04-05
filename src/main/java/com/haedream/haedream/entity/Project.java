package com.haedream.haedream.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document(collation = "TB_PROJECT")
public class Project {
    @Id
    private String id;
    private String projectName;
    private String standard;
    private LocalDateTime createDate;
    private String owner;
    
    @Builder
    public Project(String id, String projectName, String standard, LocalDateTime createDate, String owner){
        this.id = id;
        this.projectName = projectName;
        this.standard = standard;
        this.createDate = createDate;
        this.owner = owner;
    }

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

    public LocalDateTime getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    


}
