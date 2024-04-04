package com.haedream.haedream.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haedream.haedream.dto.SignUpDTO;
import com.haedream.haedream.entity.UserEntity;
import com.haedream.haedream.repository.UserRepository;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;

    // 모든 사용자의 username과 email 정보 조회
    public List<SignUpDTO> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream()
                .map(user -> {
                    SignUpDTO signUpDTO = new SignUpDTO();
                    signUpDTO.setUsername(user.getUsername());
                    signUpDTO.setEmail(user.getEmail());
                    return signUpDTO;
                })
                .collect(Collectors.toList());
    }

    // username을 기준으로 사용자 삭제
    public void deleteUserByUsername(String username) {
        userRepository.deleteByUsername(username);
    }
}
