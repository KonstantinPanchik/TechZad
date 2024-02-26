package com.example.TehZad.project.service;

import com.example.TehZad.exceptions.NotFoundException;
import com.example.TehZad.project.dto.ProjectDto;
import com.example.TehZad.project.dto.ProjectMapper;
import com.example.TehZad.project.model.Project;
import com.example.TehZad.project.repository.ProjectRepository;
import com.example.TehZad.task.model.Status;
import com.example.TehZad.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    @Autowired
    private final ProjectRepository projectRepository;


    public Project addProject(ProjectDto dto, User user) {

        boolean isExistWithSameName = projectRepository.findByName(dto.getName()).isPresent();
        if (isExistWithSameName) {
            throw new AccessDeniedException("Project with this name already exist");
        }

        Project project = ProjectMapper.from(dto);
        project.setStatus(Status.NEW);
        project.setCreated(LocalDate.now());
        project.setStatusChanged(LocalDate.now());

        project.setCreator(user);

        project.setTasks(new ArrayList<>());

        project.setChildProjects(new ArrayList<>());
        project.setParent(getParentById(dto.getParent()));

        return projectRepository.save(project);
    }


    public Project getProjectById(Long projectId) {

        return projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project  not found"));
    }


    public void deleteProject(Long projectId) {

        projectRepository.findById(projectId).orElseThrow(() -> new NotFoundException("Project not found"));
        projectRepository.deleteById(projectId);
    }

    public Project update(ProjectDto dto, Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project not found"));

        if (dto.getName() != null) {

            boolean isExistWithSameName = projectRepository.findByName(dto.getName()).isPresent();

            if (isExistWithSameName) {

                throw new AccessDeniedException("Project with this name already exist");
            }
        }

        ProjectMapper.updateFromDto(project, dto);

        return projectRepository.save(project);
    }

    public List<Project> getAll() {
        return projectRepository.findAll();
    }


    private Project getParentById(Long parentId) {
        if (parentId == null) {
            return null;
        } else {
            return projectRepository.findById(parentId)
                    .orElseThrow(() -> new NotFoundException("Parent Project with id: " + parentId + "is not found"));
        }

    }

    public List<Project> getParents() {

        return projectRepository.findParents();

    }
}
