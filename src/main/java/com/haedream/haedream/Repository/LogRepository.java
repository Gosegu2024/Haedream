package com.haedream.haedream.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.haedream.haedream.entity.Log;

@Repository
public interface LogRepository extends MongoRepository<Log, String> {

}
