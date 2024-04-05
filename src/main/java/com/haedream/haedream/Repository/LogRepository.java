package com.haedream.haedream.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.haedream.haedream.entity.Log;

@Repository
public interface LogRepository extends MongoRepository<Log, String> {
  List<Log> findByApiKeyAndProjectName(String apiKey, String projectName, Sort sort);
}
