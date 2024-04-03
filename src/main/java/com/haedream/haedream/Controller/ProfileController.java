package com.haedream.haedream.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.haedream.haedream.entity.UserEntity;
import com.haedream.haedream.model.Project;
import com.haedream.haedream.repository.UserRepository;

import ch.qos.logback.core.model.Model;

@Controller
public class ProfileController {

  @Autowired
    private UserRepository userRepository;

  @GetMapping("/profile")
  public String profile(Model model) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    UserEntity user = userRepository.findByUsername(username);
    String api_Key = user.getApi_key();
    Optional<UserEntity> optionalUserEntity = userRepository.findByApiKey(api_Key);
     if (optionalUserEntity.isPresent()) {
        UserEntity userEntity = optionalUserEntity.get();
        String api_key = userEntity.getApi_key();

        // 모델에 사용자 정보 및 프로젝트 목록 추가
        // model.addAttribute("username", username);
        // model.addAttribute("api_key", api_key);

        System.out.println(username);
        System.out.println(api_key);

    } else {
        System.out.println("요청값 없음 ");
    }

    return "profile";
  }

}
