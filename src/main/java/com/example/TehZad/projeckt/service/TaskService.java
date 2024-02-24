package com.example.TehZad.projeckt.service;

import com.example.TehZad.exceptions.NotFoundException;
import com.example.TehZad.projeckt.dto.TaskMapper;
import com.example.TehZad.projeckt.dto.TaskUpdateDto;
import com.example.TehZad.projeckt.model.Status;
import com.example.TehZad.projeckt.model.Task;
import com.example.TehZad.projeckt.repository.TaskRepository;
import com.example.TehZad.user.model.Permission;
import com.example.TehZad.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TaskService {

    TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task addTask(Task task) {
        return taskRepository.save(task);
    }


    public Task updateTask(TaskUpdateDto dto, Long taskId, User user) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Task not found"));
        if (user.getAuthorities().contains(new SimpleGrantedAuthority(Permission.CHANGE.getPermission()))
                || user.getId().equals(task.getCreator().getId())) {

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
        if (user.getAuthorities().contains(new SimpleGrantedAuthority(Permission.CHANGE.getPermission()))
                || user.getId().equals(task.getCreator().getId())) {
            taskRepository.delete(task);

        } else {
            throw new AccessDeniedException("You can't change this item");
        }
    }

    public Task updateStatus(Long taskId, String text) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Task not found"));
        Status status = Status.from(text.toUpperCase())
                .orElseThrow(() -> new NotFoundException("Status " + text + " is not exist"));
        task.setStatusChanged(LocalDate.now());
        task.setStatus(status);
        return taskRepository.save(task);
    }
}
