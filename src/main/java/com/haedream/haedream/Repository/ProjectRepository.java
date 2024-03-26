package com.haedream.haedream.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.haedream.haedream.Model.projects;

public interface ProjectRepository extends MongoRepository<projects, String> {

}
