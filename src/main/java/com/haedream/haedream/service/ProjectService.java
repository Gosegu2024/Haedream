package com.haedream.haedream.service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

import com.haedream.haedream.model.Project;
import com.haedream.haedream.repository.ProjectRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    // 모든 프로젝트를 조회
    public List<Project> getProjects() {
        return projectRepository.findAll();
    }
    
    //  프로젝트 소유자(사용자) 조회
    public List<Project> findProjectsByOwner(String owner) {
        return projectRepository.findByOwner(owner);
    }

    // 프로젝트 생성+저장
    public void projectSave(Project project) {
        project.setCreateDate(LocalDateTime.now());
        projectRepository.save(project);
    }

    // 프로젝트 삭제
    public void deleteProjects(List<String> projectIds) {
        projectIds.forEach(projectId -> {
            projectRepository.deleteById(projectId);
        });
    }

    // 프로젝트 조회
    public String getStandard(String projectId) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        Project project = projectOptional.get();
        return project.getStandard();
    }
}
