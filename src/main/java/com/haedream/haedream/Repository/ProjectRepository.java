package com.haedream.haedream.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.haedream.haedream.model.Project;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {
    List<Project> findByUsername(String username);
}
