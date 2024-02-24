package com.example.TehZad.projeckt.service;

import com.example.TehZad.exceptions.NotFoundException;
import com.example.TehZad.projeckt.dto.ProjectMapper;
import com.example.TehZad.projeckt.dto.ProjectUpdatingDto;
import com.example.TehZad.projeckt.model.Project;
import com.example.TehZad.projeckt.model.Status;
import com.example.TehZad.projeckt.repository.ProjectRepository;
import com.example.TehZad.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {


    ProjectRepository projectRepository;
    UserRepository userRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }


    public Project addProject(Project project) {
        project.setStatus(Status.NEW);
        project.setCreated(LocalDate.now());
        project.setStatusChanged(LocalDate.now());
        project.setTasks(new ArrayList<>());
        return projectRepository.save(project);
    }


    public Project getProjectById(Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new NotFoundException("Project  not found"));
        return project;
    }


    public void deleteProject(Long projectId) {
        projectRepository.findById(projectId).orElseThrow(() -> new NotFoundException("Project not found"));
        projectRepository.deleteById(projectId);
    }

    public Project update(ProjectUpdatingDto dto, Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found"));
        ProjectMapper.updateFromDto(project, dto);
        return projectRepository.save(project);

    }

    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    public Project changeProjectStatus(Long id, String text) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project not found"));
        Status status = Status.from(text.toUpperCase())
                .orElseThrow(() -> new NotFoundException("Status " + text + " is not exist"));
        project.setStatus(status);
        project.setStatusChanged(LocalDate.now());
        return projectRepository.save(project);
    }
}
