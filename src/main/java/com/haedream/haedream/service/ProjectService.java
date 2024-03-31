package com.haedream.haedream.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haedream.haedream.model.Project;
import com.haedream.haedream.repository.ProjectRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    // 지금 로그인한 사용자의 id 를 얻어옴.(spring security 적용)
    // private String getCurrentUsername() {
    // Authentication authentication =
    // SecurityContextHolder.getContext().getAuthentication();
    // if (authentication == null || !authentication.isAuthenticated()) {
    // throw new SecurityException("인증되지 않은 사용자입니다.");
    // // 즉 authentication의 return값이 null일때의 경우
    // }
    // return authentication.getName(); // 현재 로그인한 사용자의 username 반환
    // }

    // 모든 프로젝트를 조회
    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    // 프로젝트 생성
    public void projectSave(Project project) {
        project.setCreateDate(LocalDateTime.now());
        projectRepository.save(project);
    }

    // 프로젝트 삭제
    @SuppressWarnings("null")
    public void deleteProjects(List<String> projectIds) {
        projectIds.forEach(projectId -> {
            projectRepository.deleteById(projectId);
        });
    }

    // 프로젝트 조회
    @SuppressWarnings("null")
    public String getStandard(String projectId) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        Project project = projectOptional.get();
        return project.getStandard();
    }

}
