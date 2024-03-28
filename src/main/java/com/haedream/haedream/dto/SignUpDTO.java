package com.haedream.haedream.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDTO {

    private String username; // username을 id의 변수로 사용
    private String password;
    private String email;

}
