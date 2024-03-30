package com.haedream.haedream.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.haedream.haedream.model.Project;
import com.haedream.haedream.repository.ProjectRepository;

import io.jsonwebtoken.security.SecurityException;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    // 지금 로그인한 사용자의 id 를 얻어옴.(spring security 적용)
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("인증되지 않은 사용자입니다.");
            // 즉 authentication의 return값이 null일때의 경우
        }
        return authentication.getName(); // 현재 로그인한 사용자의 username 반환
    }

    // 프로젝트 생성
    public Project saveProject(String projectName, String standard) {
        String username = getCurrentUsername();
        Project project = new Project();
        project.setProjectName(projectName);
        project.setCreateDate(LocalDateTime.now());
        project.setUsername(username);
        project.setStandard(standard);

        return projectRepository.save(project);
    }

    // 프로젝트 삭제
    @SuppressWarnings("null")
    public void deleteProjects(List<String> projectIds) {
        String username = getCurrentUsername();
        // 각 프로젝트마다 삭제 권한 검사 후 삭제실행됨
        projectIds.forEach(projectId -> { // 여기에 주의 뜨는데 걍 냅둬야함 ㅇㅇ
            projectRepository.findById(projectId).ifPresent(project -> {
                if (project.getUsername().equals(username)) {
                    projectRepository.deleteById(projectId);
                } else {
                    throw new SecurityException("삭제 권한이 없습니다.");
                }
            });
        });
    }

    // 모든 프로젝트를 조회
    public List<Project> findAll() {
        String username = getCurrentUsername();
        return projectRepository.findByUsername(username);
    }

}
