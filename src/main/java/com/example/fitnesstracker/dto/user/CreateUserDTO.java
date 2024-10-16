package com.example.fitnesstracker.dto.user;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateUserDTO implements Serializable {

    @NotEmpty(message = "Username is required")
    private String username;
    @NotEmpty(message = "Password is required")
    private String password;
    @NotEmpty(message = "Email is required")
    private String email;

}
