package com.haedream.haedream.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

import com.haedream.haedream.model.Project;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {

    List<Project> findByUsername(String username);

    List<Project> findByOwner(String owner);

    List<Project> findByProjectName(String projectName);

    Optional<Project> findByprojectName(String projectName);

    List<Project> findByProjectNameAndOwner(String projectName, String owner);

}

