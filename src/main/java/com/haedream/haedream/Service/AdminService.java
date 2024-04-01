package com.haedream.haedream.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haedream.haedream.entity.UserEntity;
import com.haedream.haedream.repository.UserRepository;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;

}
