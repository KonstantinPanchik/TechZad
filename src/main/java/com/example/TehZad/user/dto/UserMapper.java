package com.example.TehZad.user.dto;

import com.example.TehZad.user.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMapper {

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public static User fromCreationDto(UserCreationDto dto) {
        User user = User.builder()
                .username(dto.getName())
                .password(encoder.encode(dto.getPassword()))
                .role(dto.getRole())
                .build();

        return user;
    }

    public static UserResponseDto toDto(User user) {
        UserResponseDto dto = UserResponseDto.builder()
                .id(user.getId())
                .name(user.getUsername())
                .role(user.getRole().toString())
                .build();
        return dto;
    }
}
