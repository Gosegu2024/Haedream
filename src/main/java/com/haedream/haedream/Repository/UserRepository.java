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

    @Query(value = "{ 'api_key' : ?0 }")
    Optional<UserEntity> findByApiKey(String apiKey);

    @SuppressWarnings("null")
    List<UserEntity> findAll();

    void deleteByUsername(String username);

    UserEntity findByEmail(String email);

}