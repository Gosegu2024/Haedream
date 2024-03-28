package com.haedream.haedream.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.haedream.haedream.model.Project;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {

}
