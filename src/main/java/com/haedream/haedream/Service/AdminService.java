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

    public void deleteUserByUsername(String username) {
        userRepository.deleteByUsername(username);
    }
}
