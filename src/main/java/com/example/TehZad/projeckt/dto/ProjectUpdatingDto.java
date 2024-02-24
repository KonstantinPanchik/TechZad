package com.example.TehZad.projeckt.dto;

import com.example.TehZad.projeckt.model.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProjectUpdatingDto {


    @Size(min = 10, max = 200)
    private String name;

    @Size(min = 10, max = 200)
    private String description;

    @Enumerated(value = EnumType.STRING)
    private Status status;

}
