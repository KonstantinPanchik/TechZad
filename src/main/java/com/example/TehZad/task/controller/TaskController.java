package com.example.TehZad.task.controller;


import com.example.TehZad.task.dto.TaskMapper;
import com.example.TehZad.task.dto.TaskUpdateDto;
import com.example.TehZad.task.model.Task;
import com.example.TehZad.task.service.TaskService;
import com.example.TehZad.user.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;


    @PreAuthorize("isAuthenticated()")
    @GetMapping("{id}")
    public ResponseEntity getTaskById(@PathVariable Long id) {

        return ResponseEntity.ok(TaskMapper.toResponseDto(taskService.getById(id)));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity addTask(@RequestBody @Valid Task task, @AuthenticationPrincipal User user) {

        task = taskService.addTask(task, user);
        return ResponseEntity.ok(TaskMapper.toResponseDto(task));
    }

    @PatchMapping("{id}")
    public ResponseEntity updateTask(@PathVariable Long id,
                                     @RequestBody TaskUpdateDto dto,
                                     @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(TaskMapper.toResponseDto(taskService.updateTask(dto, id, user)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteTask(@PathVariable Long id,
                                     @AuthenticationPrincipal User user) {
        taskService.deleteTask(id, user);
        return ResponseEntity.ok("Task + " + id + " has been deleted");
    }

    @PostMapping("{id}/newStatus")
    public ResponseEntity changeStatus(@PathVariable Long id,
                                       @RequestParam(required = false, defaultValue = "IN_PROGRESS") String status) {

        return ResponseEntity.ok(TaskMapper.toResponseDto(taskService.updateStatus(id, status)));
    }


}
