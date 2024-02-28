package com.example.TehZad.project.dto;

import com.example.TehZad.task.dto.TaskMapper;
import com.example.TehZad.project.model.Project;
import com.example.TehZad.user.dto.UserMapper;

public class ProjectMapper {


    public static Project updateFromDto(Project project, ProjectDto dto) {
        if (dto.getName() != null) {
            project.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            project.setDescription(dto.getDescription());
        }
        if (dto.getStatus() != null) {
            project.setStatus(dto.getStatus());
        }
        return project;
    }

    public static ProjectResponseDto toDto(Project project) {
        ProjectResponseDto dto = ProjectResponseDto.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .status(project.getStatus())
                .statusChanged(project.getStatusChanged())
                .created(project.getCreated())
                .creator(UserMapper.toDto(project.getCreator()))
                .children(project.getChildProjects().stream().map(ProjectMapper::toDto).toList())
                .tasks(project.getTasks().stream().map(TaskMapper::toResponseDto).toList())
                .build();
        return dto;
    }

    public static Project from(ProjectDto dto) {
        Project project = new Project();
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        return project;
    }
}

