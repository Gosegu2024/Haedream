package com.haedream.haedream.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.haedream.haedream.entity.projects;

public interface ProjectRepository extends MongoRepository<projects, String> {

}
