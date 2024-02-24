package com.example.TehZad.projeckt.controller;


import com.example.TehZad.projeckt.dto.TaskMapper;
import com.example.TehZad.projeckt.dto.TaskUpdateDto;
import com.example.TehZad.projeckt.model.Status;
import com.example.TehZad.projeckt.model.Task;
import com.example.TehZad.projeckt.service.TaskService;
import com.example.TehZad.user.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    //любой
    @GetMapping("{id}")
    public ResponseEntity getTaskById(@PathVariable Long id) {
        Task task = taskService.getById(id);

        return ResponseEntity.ok(TaskMapper.toResponseDto(task));
    }

    //любой но должен запомнить юзера
    @PostMapping
    public ResponseEntity addTask(@RequestBody @Valid Task task, @AuthenticationPrincipal User user) {
        task.setCreator(user);
        task.setCreated(LocalDate.now());
        task.setStatus(Status.NEW);
        task.setStatusChanged(LocalDate.now());
        task = taskService.addTask(task);
        return ResponseEntity.ok(TaskMapper.toResponseDto(task));
    }

    @PatchMapping("{id}")
    public ResponseEntity updateTask(@PathVariable Long id,
                                     @RequestBody TaskUpdateDto dto,
                                     @AuthenticationPrincipal User user) {
        Task task = taskService.updateTask(dto, id, user);
        return ResponseEntity.ok(TaskMapper.toResponseDto(task));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteTask(@PathVariable Long id,
                                     @AuthenticationPrincipal User user) {
        taskService.deleteTask(id, user);
        return ResponseEntity.ok("Task + " + id + " has been deleted");
    }

    @PostMapping("{id}/status")
    public ResponseEntity changeStatus(@PathVariable Long id,
                                       @RequestParam(required = false, defaultValue = "IN_PROGRESS") String text) {

        Task task = taskService.updateStatus(id, text);
        return ResponseEntity.ok(TaskMapper.toResponseDto(task));
    }


}
