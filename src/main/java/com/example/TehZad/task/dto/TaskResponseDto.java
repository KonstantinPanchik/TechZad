package com.example.TehZad.task.dto;

import com.example.TehZad.task.model.Status;
import com.example.TehZad.task.model.TaskType;
import com.example.TehZad.user.dto.UserResponseDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class TaskResponseDto {

    Long id;

    String name;

    String description;

    Long projectId;

    LocalDate created;

    LocalDate statusChanged;

    UserResponseDto creator;

    Status status;

    TaskType taskType;
}