package com.example.TehZad.project;

import com.example.TehZad.project.dto.ProjectDto;
import com.example.TehZad.project.dto.ProjectMapper;
import com.example.TehZad.project.model.Project;
import com.example.TehZad.project.service.ProjectService;
import com.example.TehZad.user.model.User;
import com.example.TehZad.validation.UpdationInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
@Slf4j
public class ProjectController {

    private final ProjectService projectService;


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity getProjectById(@PathVariable Long id) {

        return ResponseEntity.ok(ProjectMapper.toDto(projectService.getProjectById(id)));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity getProjects() {

        return ResponseEntity.ok(projectService.getAll().stream().map(ProjectMapper::toDto).toList());
    }

    @PreAuthorize("hasAuthority('change')")
    @PostMapping
    public ResponseEntity createProject(@RequestBody @Valid ProjectDto projectDto,
                                        @AuthenticationPrincipal User user) {

        return ResponseEntity.ok(ProjectMapper.toDto(projectService.addProject(projectDto, user)));
    }

    @PreAuthorize("hasAuthority('change')")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteProject(@PathVariable Long id) {

        projectService.deleteProject(id);
        return ResponseEntity.ok("Task has been deleted");
    }

    @PreAuthorize("hasAuthority('change')")
    @PatchMapping("/{id}")
    public ResponseEntity updateProject(@RequestBody @Validated(UpdationInfo.class) ProjectDto dto,
                                        @PathVariable Long id) {

        Project project = projectService.update(dto, id);
        return ResponseEntity.ok(ProjectMapper.toDto(project));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/parents")
    public ResponseEntity getParentProjects() {

        return ResponseEntity.ok(projectService.getParents().stream().map(ProjectMapper::toDto).toList());
    }

}
