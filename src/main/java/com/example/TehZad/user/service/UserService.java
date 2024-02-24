package com.example.TehZad.user.service;

import com.example.TehZad.exceptions.NotFoundException;
import com.example.TehZad.user.model.User;

import com.example.TehZad.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("userService")
public class UserService implements UserDetailsService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new NotFoundException("User not found");
        }

        return user;
    }

    public User addUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new AccessDeniedException("User name is occupied");
        }

        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " is not exist"));
        userRepository.delete(user);
    }

    public void deleteUserByName(String name) {
        User user = (User) loadUserByUsername(name);
        userRepository.delete(user);
    }

    public User getUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElseThrow(() -> new NotFoundException("User not found"));
    }


    public List<User> getAll() {
        return userRepository.findAll();
    }

    public List<User> search(String text) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        return userRepository.findWhereName(text);
    }

}
