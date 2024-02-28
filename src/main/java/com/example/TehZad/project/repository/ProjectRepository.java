package com.example.TehZad.project.repository;

import com.example.TehZad.project.model.Project;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Override
    @EntityGraph("Project.creatorAndTasks")
    List<Project> findAll();

    @Override
    @EntityGraph("Project.creatorAndTasks")
    Optional<Project> findById(Long aLong);

    @EntityGraph("Project.creatorAndTasks")
    Optional<Project> findByName(String name);

    @EntityGraph("Project.creatorAndTasks")
    @Query("select p from Project as p WHERE p.parent is null")
    List<Project> findParents();
}
