package com.haedream.haedream.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.haedream.haedream.entity.UserEntity;

public interface UserRepository extends MongoRepository<UserEntity, String> {

    Boolean existsByUsername(String id);

    // username을 받아 DB 테이블에서 회원을 조회하는 메소드 작성
    UserEntity findByUsername(String id);

    // MongoDB의 'api_key' 필드를 사용해 UserEntity를 조회하는 쿼리 메소드, 변수명 일치하지 않아 @query사용
    @Query(value = "{ 'api_key' : ?0 }")
    Optional<UserEntity> findByApiKey(String apiKey);
}