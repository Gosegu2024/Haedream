package com.haedream.haedream.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.haedream.haedream.entity.UserEntity;

public interface UserRepository extends MongoRepository<UserEntity, String> {

    Boolean existsByUsername(String id);

    // username을 받아 DB 테이블에서 회원을 조회하는 메소드 작성
    UserEntity findByUsername(String id);
}