package com.haedream.haedream.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haedream.haedream.Model.projects;
import com.haedream.haedream.Repository.ProjectRepository;

@Service
public class projectsServiceImpl implements projectsService {

    private final ProjectRepository projectRepository;

    @Autowired
    public projectsServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<projects> findAllprojects() {
        return projectRepository.findAll();
    }

}
