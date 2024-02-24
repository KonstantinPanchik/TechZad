package com.example.TehZad.projeckt.dto;

import com.example.TehZad.projeckt.model.Status;
import com.example.TehZad.projeckt.model.TaskType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskUpdateDto {


    @Size(min = 20, max = 200)
    String name;


    @Size(min = 40, max = 512)
    String description;


    @Enumerated(value = EnumType.STRING)
    Status status;

    @Enumerated(value = EnumType.STRING)
    TaskType taskType;
}
