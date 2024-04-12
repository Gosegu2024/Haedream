package com.haedream.haedream.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "TB_PROJECT")
public class Project {
    @Id
    private String id;
    private String projectName;
    private String standard;
    private ZonedDateTime createDate;
    private String owner;

    @Builder
    public Project(String id, String projectName, String standard, ZonedDateTime createDate, String owner) {
        this.id = id;
        this.projectName = projectName;
        this.standard = standard;
        this.createDate = createDate.withZoneSameInstant(ZoneId.of("Asia/Seoul")); // 서울 시간대로 설정
        this.owner = owner;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate.withZoneSameInstant(ZoneId.of("Asia/Seoul")); // 서울 시간대로 설정
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

    public ZonedDateTime getCreateDate() {
        return this.createDate;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

}