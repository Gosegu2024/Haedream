package com.haedream.haedream.service;

import java.util.List;
import java.util.Optional;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.haedream.haedream.model.Project;
import com.haedream.haedream.repository.ProjectRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    public List<Project> findProjectsByOwner(String owner) {
        return projectRepository.findByOwner(owner);
    }

    public void projectSave(Project project) {
        project.setCreateDate(ZonedDateTime.now(ZoneId.of("Asia/Seoul")));
        projectRepository.save(project);
    }

    public void deleteProjects(List<String> projectIds) {
        projectIds.forEach(projectId -> {
            projectRepository.deleteById(projectId);
        });
    }

    public String getStandard(String projectId) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        Project project = projectOptional.get();
        return project.getStandard();
    }
}
