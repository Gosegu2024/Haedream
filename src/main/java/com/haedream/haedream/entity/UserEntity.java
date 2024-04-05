package com.haedream.haedream.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "TB_USER")
@Data
public class UserEntity {

    private String username; // id를 username 변수로 사용
    private String email;
    private String password;
    private String role;
    private String api_key;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getApi_key() {
        return this.api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

}
