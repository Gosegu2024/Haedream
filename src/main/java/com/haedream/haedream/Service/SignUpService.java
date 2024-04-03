package com.haedream.haedream.service;


import java.security.SecureRandom;
import java.util.DuplicateFormatFlagsException;

import com.haedream.haedream.dto.SignUpDTO;
import com.haedream.haedream.entity.UserEntity;
import com.haedream.haedream.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class SignUpService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SignUpService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void signupProcess(SignUpDTO signupDTO) throws DuplicateFormatFlagsException {

        String id = signupDTO.getUsername();
        String password = signupDTO.getPassword();
        String email = signupDTO.getEmail();

        UserEntity isExist = userRepository.findByUsername(id);
        if (isExist != null) {

            throw new DuplicateFormatFlagsException(id);
        }

        UserEntity data = new UserEntity();
        data.setUsername(id);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setEmail(email);
        data.setRole("ROLE_USER");
        // 랜덤한 문자열 생성 및 설정(중복가능)
        String apiKey = generateRandomString(20); 
        data.setApi_key(apiKey);

        userRepository.save(data);
    }

    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-*!@&";
        StringBuilder sb = new StringBuilder(length);
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }
}