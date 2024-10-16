package com.example.fitnesstracker.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseUserDTO implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String email;
}
