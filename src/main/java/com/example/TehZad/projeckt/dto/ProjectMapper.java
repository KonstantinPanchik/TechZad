package com.example.TehZad.projeckt.dto;

import com.example.TehZad.projeckt.model.Project;
import com.example.TehZad.user.dto.UserMapper;

public class ProjectMapper {


    public static Project updateFromDto(Project project, ProjectUpdatingDto dto) {
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
        return ProjectResponseDto.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .status(project.getStatus())
                .statusChanged(project.getStatusChanged())
                .created(project.getCreated())
                .creator(UserMapper.toDto(project.getCreator()))
                .tasks(project.getTasks().stream().map(TaskMapper::toResponseDto).toList())
                .build();
    }
}

