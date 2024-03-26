package com.haedream.haedream.Model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "TB_PROJECT")
public class projects {
    @Id
    private String id; // MongoDB고유 식별자(걍 저절로 생김..)

    private String PJ_IDX;
    private String PJ_NM;
    private String USER_ID;
    private Date CREATE_DATE;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPJ_IDX() {
        return this.PJ_IDX;
    }

    public void setPJ_IDX(String PJ_IDX) {
        this.PJ_IDX = PJ_IDX;
    }

    public String getPJ_NM() {
        return this.PJ_NM;
    }

    public void setPJ_NM(String PJ_NM) {
        this.PJ_NM = PJ_NM;
    }

    public String getUSER_ID() {
        return this.USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public Date getCREATE_DATE() {
        return this.CREATE_DATE;
    }

    public void setCREATE_DATE(Date CREATE_DATE) {
        this.CREATE_DATE = CREATE_DATE;
    }
}
