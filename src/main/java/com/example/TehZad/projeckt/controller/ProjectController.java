package com.example.TehZad.projeckt.controller;

import com.example.TehZad.projeckt.dto.ProjectMapper;
import com.example.TehZad.projeckt.dto.ProjectUpdatingDto;
import com.example.TehZad.projeckt.model.Project;
import com.example.TehZad.projeckt.service.ProjectService;
import com.example.TehZad.user.model.User;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/project")
@Slf4j
public class ProjectController {

    ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    //любой
    @GetMapping("/{id}")
    public ResponseEntity getProjectById(@PathVariable Long id) {

        return ResponseEntity.ok(ProjectMapper.toDto(projectService.getProjectById(id)));
    }

    //любой
    @GetMapping
    public ResponseEntity getProjects() {

        return ResponseEntity.ok(projectService.getAll().stream().map(ProjectMapper::toDto).toList());
    }

    //админ
    @PostMapping
    public ResponseEntity createProject(@RequestBody @Valid Project project, @AuthenticationPrincipal User user) {

        project.setCreator(user);

        project = projectService.addProject(project);
        return ResponseEntity.ok(ProjectMapper.toDto(project));
    }

    //админ
    @PostMapping("/{id}/status")
    public ResponseEntity changeProjectStatus(@RequestParam(required = true, name = "text") String text, @PathVariable Long id) {

        Project project = projectService.changeProjectStatus(id, text);
        return ResponseEntity.ok(ProjectMapper.toDto(project));
    }

    //админ
    @DeleteMapping("/{id}")
    public ResponseEntity deleteProject(@PathVariable Long id) {

        projectService.deleteProject(id);
        return ResponseEntity.ok("Task has been deleted");
    }

    //админ
    @PatchMapping("/{id}")
    public ResponseEntity updateProject(@RequestBody @Valid ProjectUpdatingDto dto, @PathVariable Long id) {

        Project project = projectService.update(dto, id);
        return ResponseEntity.ok(ProjectMapper.toDto(project));
    }
}
