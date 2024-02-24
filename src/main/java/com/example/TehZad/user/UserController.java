package com.example.TehZad.user;

import com.example.TehZad.user.dto.UserCreationDto;
import com.example.TehZad.user.dto.UserMapper;
import com.example.TehZad.user.dto.UserResponseDto;
import com.example.TehZad.user.model.User;
import com.example.TehZad.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {


    UserService userService;

    @Autowired
    public UserController(@Qualifier("userService") UserService userService) {
        this.userService = userService;
    }

    @PostMapping//только админ
    public ResponseEntity createUser(@RequestBody @Valid UserCreationDto dto) {
        User user = UserMapper.fromCreationDto(dto);
        user = userService.addUser(user);

        return ResponseEntity.ok(UserMapper.toDto(user));
    }

    @DeleteMapping("/{id}")//только админ
    public ResponseEntity deleteUserById(@PathVariable Long id) {

        userService.deleteUserById(id);
        return ResponseEntity.ok("User has been deleted successfully ");
    }

    @GetMapping("/{id}")//любой
    public ResponseEntity getUserById(@PathVariable Long id) {

        User user = userService.getUserById(id);

        return ResponseEntity.ok(UserMapper.toDto(user));
    }

    @GetMapping("/myprofile")//любой
    public ResponseEntity getUserById(@AuthenticationPrincipal User user) {

        return ResponseEntity.ok(UserMapper.toDto(user));
    }


    @GetMapping//любой
    public ResponseEntity getAllUsers() {

        List<UserResponseDto> users = userService.getAll().stream().map(UserMapper::toDto).toList();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/search")//любой
    public ResponseEntity search(@RequestParam String text) {

        List<UserResponseDto> users = userService.search(text).stream().map(UserMapper::toDto).toList();

        return ResponseEntity.ok(users);
    }


}
