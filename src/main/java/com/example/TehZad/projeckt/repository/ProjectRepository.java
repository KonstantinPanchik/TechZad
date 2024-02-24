package com.example.TehZad.projeckt.repository;

import com.example.TehZad.projeckt.model.Project;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Override
    @EntityGraph("Project.creatorAndTasks")
    List<Project> findAll();

    @Override
    @EntityGraph("Project.creatorAndTasks")
    Optional<Project> findById(Long aLong);

}
