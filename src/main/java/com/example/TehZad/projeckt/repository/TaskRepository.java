package com.example.TehZad.projeckt.repository;

import com.example.TehZad.projeckt.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
