package com.example.TehZad.project.dto;

import com.example.TehZad.task.dto.TaskResponseDto;
import com.example.TehZad.task.model.Status;
import com.example.TehZad.user.dto.UserResponseDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ProjectResponseDto {


    private Long id;

    private String name;

    private String description;

    LocalDate created;

    LocalDate statusChanged;

    private UserResponseDto creator;

    private List<ProjectResponseDto> children;

    private List<TaskResponseDto> tasks;

    private Status status;
}