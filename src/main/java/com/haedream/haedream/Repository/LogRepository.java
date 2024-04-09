package com.haedream.haedream.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.haedream.haedream.entity.Log;

@Repository
public interface LogRepository extends MongoRepository<Log, String> {
  List<Log> findByApiKeyAndProjectNameAndIsItEval(String apiKey, String projectName, String isItEval, Sort sort);

  List<Log> deleteByApiKeyAndProjectNameAndId(String apiKey, String projectName, String id);

  @SuppressWarnings("null")
  Optional<Log> findById(String id);

  List<Log> findByApiKeyAndProjectNameAndIsItEval(String apiKey, String projectName, String IsItEval);

}
