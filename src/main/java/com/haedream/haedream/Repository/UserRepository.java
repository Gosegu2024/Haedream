package com.haedream.haedream.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.haedream.haedream.entity.UserEntity;

public interface UserRepository extends MongoRepository<UserEntity, String> {

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    UserEntity findByUsername(String username);

    // MongoDB의 'api_key' 필드를 사용해 UserEntity를 조회하는 쿼리 메소드, 변수명 일치하지 않아 @query사용
    @Query(value = "{ 'api_key' : ?0 }")
    Optional<UserEntity> findByApiKey(String apiKey);

    // 관리자만 사용가능한 전체 회원 조회기능
    @SuppressWarnings("null")
    List<UserEntity> findAll();

    // 관리자만 사용자 삭제
    void deleteByUsername(String username);

    UserEntity findByEmail(String email);

}