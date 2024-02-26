package com.example.TehZad.task.dto;

import com.example.TehZad.task.model.Task;
import com.example.TehZad.user.dto.UserMapper;

public class TaskMapper {


    public static Task updateFromDto(Task task, TaskUpdateDto dto) {
        if (dto.getName() != null) {
            task.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            task.setDescription(dto.getDescription());
        }
        if (dto.getStatus() != null) {
            task.setStatus(dto.getStatus());
        }
        if (dto.getTaskType() != null) {
            task.setTaskType(dto.getTaskType());
        }
        return task;
    }

    public static TaskResponseDto toResponseDto(Task task) {
        return TaskResponseDto.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .projectId(task.getProjectId())
                .statusChanged(task.getStatusChanged())
                .status(task.getStatus())
                .created(task.getCreated())
                .taskType(task.getTaskType())
                .creator(UserMapper.toDto(task.getCreator()))
                .build();

    }
}
