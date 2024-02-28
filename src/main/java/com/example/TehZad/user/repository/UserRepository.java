package com.example.TehZad.user.repository;

import com.example.TehZad.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User as u WHERE u.username=?1")
    Optional<User> findByUsername(String username);

    @Query("select u from User as u WHERE upper(u.username) like upper(concat('%', ?1, '%'))")
    List<User> findWhereName(String text);



}
