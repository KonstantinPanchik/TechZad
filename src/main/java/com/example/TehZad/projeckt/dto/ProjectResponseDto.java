package com.example.TehZad.projeckt.dto;

import com.example.TehZad.projeckt.model.Status;
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


    private List<TaskResponseDto> tasks;

    private Status status;
}