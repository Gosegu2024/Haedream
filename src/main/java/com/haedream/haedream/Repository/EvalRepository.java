package com.haedream.haedream.repository;

import com.haedream.haedream.entity.Eval;

import java.util.Optional;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvalRepository extends MongoRepository<Eval, String> {

  Optional<Eval> deleteEvalsByLogId(String logId);

  @SuppressWarnings("null")
  Optional<Eval> findById(String evalId);

  Eval findOneByLogId(String logId);

  Eval findOneByLogIdAndUsernameAndProjectName(String logId, String username, String projectName);

  Eval findOneById(String evalId);

  Eval deleteByLogId(String logId);

  List<Eval> findByProjectNameAndUsername(String projectName, String username, Sort sort);
}
