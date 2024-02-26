package com.example.TehZad.task.service;

import com.example.TehZad.exceptions.NotFoundException;
import com.example.TehZad.task.dto.TaskMapper;
import com.example.TehZad.task.dto.TaskUpdateDto;
import com.example.TehZad.task.model.Status;
import com.example.TehZad.task.model.Task;
import com.example.TehZad.task.repository.TaskRepository;
import com.example.TehZad.user.model.Permission;
import com.example.TehZad.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task addTask(Task task, User user) {

        task.setCreator(user);
        task.setCreated(LocalDate.now());
        task.setStatus(Status.NEW);
        task.setStatusChanged(LocalDate.now());

        return taskRepository.save(task);
    }


    public Task updateTask(TaskUpdateDto dto, Long taskId, User user) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Task not found"));

        boolean contains = user.getAuthorities().contains(new SimpleGrantedAuthority(Permission.CHANGE.getPermission()));
        boolean equals = user.getId().equals(task.getCreator().getId());
        if (contains || equals) {

            task = TaskMapper.updateFromDto(task, dto);

            return taskRepository.save(task);

        } else {
            throw new AccessDeniedException("You can't change this item");
        }
    }

    public Task getById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task with id " + id + "not found"));
    }

    public void deleteTask(Long taskId, User user) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Task not found"));

        boolean contains = user.getAuthorities().contains(new SimpleGrantedAuthority(Permission.CHANGE.getPermission()));
        boolean equals = user.getId().equals(task.getCreator().getId());

        if (contains || equals) {
            taskRepository.delete(task);

        } else {
            throw new AccessDeniedException("You can't change this item");
        }
    }

    public Task updateStatus(Long taskId, String status) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Task not found"));

        Status newStatus = Status.valueOf(status.toUpperCase());
        task.setStatusChanged(LocalDate.now());
        task.setStatus(newStatus);
        return taskRepository.save(task);
    }
}
