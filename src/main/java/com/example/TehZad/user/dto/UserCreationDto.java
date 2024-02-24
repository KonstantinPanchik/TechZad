package com.example.TehZad.user.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserCreationDto {

    @NotBlank
    private String name;
    @NotBlank
    private String password;
    @NotBlank
    private String confirmPassword;
    @NotNull
    private Boolean isAdmin;


    @AssertTrue
    public boolean isPasswordSame() {
        if (password == null || confirmPassword == null) {
            return false;
        }
        return password.equals(confirmPassword);

    }
}
