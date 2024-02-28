package com.example.TehZad.project.model;

import com.example.TehZad.task.model.Status;
import com.example.TehZad.task.model.Task;
import com.example.TehZad.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_project")
@Data
@NamedEntityGraph(name = "Project.creatorAndTasks", attributeNodes = {@NamedAttributeNode("creator"),
        @NamedAttributeNode("tasks")})
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    @NotBlank
    @Size(min = 10, max = 200)
    @Column
    private String description;

    private LocalDate created;
    @Column(name = "STATUS_CHANGED")
    private LocalDate statusChanged;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator")
    private User creator;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")//проджект id подписанно тк это прекрепляется из t_task по полу project_id
    private List<Task> tasks;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id") //древовидная структура проекта
    private Project parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER) //древовидная структура проекта
    private List<Project> childProjects = new ArrayList<>();

    public Project() {
    }

    public Project(Long id,
                   String name,
                   String description,
                   LocalDate created,
                   LocalDate statusChanged,
                   User creator,
                   List<Task> tasks,
                   Status status,
                   Project parent) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
        this.statusChanged = statusChanged;
        this.creator = creator;
        this.tasks = tasks;
        this.status = status;
        this.parent = parent;
    }
}
