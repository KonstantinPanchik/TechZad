package com.example.TehZad.task.model;

import com.example.TehZad.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "t_task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank
    @Size(min = 20, max = 200)
    String name;

    @NotBlank
    @Size(min = 20, max = 512)
    String description;

    @NotNull
    @Column(name = "project_id")
    Long projectId;

    LocalDate created;
    @Column(name = "STATUS_CHANGED")
    LocalDate statusChanged;

    @ManyToOne
    @JoinColumn(name = "creator")
    User creator;

    @Enumerated(value = EnumType.STRING)
    Status status;

    @Enumerated(value = EnumType.STRING)
    TaskType taskType;
}
