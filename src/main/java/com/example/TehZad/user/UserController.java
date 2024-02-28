package com.example.TehZad.user;

import com.example.TehZad.user.dto.UserCreationDto;
import com.example.TehZad.user.dto.UserMapper;
import com.example.TehZad.user.dto.UserResponseDto;
import com.example.TehZad.user.model.User;
import com.example.TehZad.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;


    @PreAuthorize("hasAuthority('change')")
    @PostMapping
    public ResponseEntity createUser(@RequestBody @Valid UserCreationDto dto) {
        User user = UserMapper.fromCreationDto(dto);

        return ResponseEntity.ok(UserMapper.toDto(userService.addUser(user)));
    }

    @PreAuthorize("hasAuthority('change')")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUserById(@PathVariable Long id) {

        userService.deleteUserById(id);
        return ResponseEntity.ok("User has been deleted successfully ");
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity getUserById(@PathVariable Long id) {

        return ResponseEntity.ok(UserMapper.toDto(userService.getUserById(id)));
    }

    @GetMapping("/myprofile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity getUserById(@AuthenticationPrincipal User user) {

        return ResponseEntity.ok(UserMapper.toDto(user));
    }


    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity getAllUsers() {

        List<UserResponseDto> users = userService.getAll().stream().map(UserMapper::toDto).toList();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/search")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity search(@RequestParam String text) {

        List<UserResponseDto> users = userService.search(text).stream().map(UserMapper::toDto).toList();

        return ResponseEntity.ok(users);
    }


}
