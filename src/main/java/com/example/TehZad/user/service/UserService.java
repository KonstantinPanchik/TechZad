package com.example.TehZad.user.service;

import com.example.TehZad.exceptions.NotFoundException;
import com.example.TehZad.user.model.User;
import com.example.TehZad.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public User addUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new AccessDeniedException("User with name " + user.getUsername() + " already exist");
        }
        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " is not exist"));
        userRepository.delete(user);
    }


    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
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
