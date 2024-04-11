package com.haedream.haedream.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.haedream.haedream.entity.Eval;
import com.haedream.haedream.repository.EvalRepository;

import java.util.List;

@Service
public class AnalyzeService {

  @Autowired
  private EvalRepository evalRepository;

  public List<Eval> findByProjectNameAndUsernameOrderByEvalLogDateDesc(String projectName, String username) {
    Sort sortByEvalLogDateDesc = Sort.by(Sort.Direction.DESC, "evalLogDate");
    return evalRepository.findByProjectNameAndUsername(projectName, username, sortByEvalLogDateDesc);
  }
}