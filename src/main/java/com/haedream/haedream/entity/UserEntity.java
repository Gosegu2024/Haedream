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
}
