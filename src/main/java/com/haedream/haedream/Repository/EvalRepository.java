package com.haedream.haedream.repository;

import com.haedream.haedream.entity.Eval;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvalRepository extends MongoRepository<Eval, String> {
    
}
