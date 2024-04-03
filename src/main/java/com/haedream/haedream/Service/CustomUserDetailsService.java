package com.haedream.haedream.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.haedream.haedream.dto.CustomUserDetails;
import com.haedream.haedream.entity.UserEntity;
import com.haedream.haedream.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 사용자 이름으로 데이터베이스에서 사용자 정보를 찾는다.
        UserEntity userData = userRepository.findByUsername(username);

        System.out.println(userData);
        
        // 사용자 정보가 존재하는 경우 UserDetails를 구현한 CustomUserDetails 객체로 변환하여 반환
        if (userData != null) {

            return new CustomUserDetails(userData);
        }
        // 사용자 정보가 없는 경우 예외처리
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
