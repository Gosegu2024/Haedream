package com.haedream.haedream.service;

import java.security.SecureRandom;
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

    public void signupProcess(SignUpDTO signupDTO) {
        if (signupDTO.getUsername() == null || signupDTO.getUsername().trim().isEmpty()) {
            throw new IllegalStateException("아이디를 입력해주세요.");
        }

        if (userRepository.existsByUsername(signupDTO.getUsername())) {
            throw new IllegalStateException("이미 사용중인 아이디입니다.");
        }

        if (signupDTO.getEmail() == null || signupDTO.getEmail().trim().isEmpty()) {
            throw new IllegalStateException("이메일을 입력해주세요.");
        }

        if (userRepository.existsByEmail(signupDTO.getEmail())) {
            throw new IllegalStateException("이미 사용중인 이메일입니다.");
        }

        if ((signupDTO.getPassword() == null || signupDTO.getPassword().trim().isEmpty()) ||
                (signupDTO.getConfirmPassword() == null || signupDTO.getConfirmPassword().trim().isEmpty())) {
            throw new IllegalStateException("비밀번호를 입력해주세요.");
        }

        if (!signupDTO.getPassword().equals(signupDTO.getConfirmPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        UserEntity newUser = new UserEntity();
        newUser.setUsername(signupDTO.getUsername());
        newUser.setPassword(bCryptPasswordEncoder.encode(signupDTO.getPassword()));
        newUser.setEmail(signupDTO.getEmail());
        newUser.setRole("ROLE_USER");
        newUser.setApi_key(generateRandomString(20));

        userRepository.save(newUser);
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
