package com.example.TehZad.projeckt.model;

import com.example.TehZad.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
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
    @NotBlank
    @Size(min = 10, max = 200)
    @Column
    private String name;
    @NotBlank
    @Size(min = 10, max = 200)
    @Column
    private String description;

    LocalDate created;
    @Column(name = "STATUS_CHANGED")
    LocalDate statusChanged;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator")
    private User creator;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private List<Task> tasks;

    @Enumerated(value = EnumType.STRING)
    private Status status;

}
