package com.haedream.haedream.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.haedream.haedream.model.Project;
import com.haedream.haedream.repository.ProjectRepository;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    // 프로젝트 생성
    public Project saveProject(String projectName) {
        Project project = new Project();
        project.setProjectName(projectName);
        project.setCreateDate(LocalDateTime.now());

        return projectRepository.save(project);
    }

    // 프로젝트 삭제
    public void deleteProjects(List<String> projectIds) {
        for (String projectId : projectIds) {
            projectRepository.deleteById(projectId);
        }
    }

    // 모든 프로젝트를 조회
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

}
