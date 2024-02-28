package com.example.TehZad.project.dto;

import com.example.TehZad.task.model.Status;
import com.example.TehZad.validation.CreationInfo;
import com.example.TehZad.validation.UpdationInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProjectDto {

    @NotBlank(groups = CreationInfo.class)
    @Size(min = 5, max = 200, groups = UpdationInfo.class)
    private String name;
    @NotBlank(groups = CreationInfo.class)
    @Size(min = 10, max = 200, groups = UpdationInfo.class)
    private String description;

    private Status status;

    private Long parent;
}
