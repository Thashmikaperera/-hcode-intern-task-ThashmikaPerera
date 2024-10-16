package com.example.fitnesstracker.service;

import com.example.fitnesstracker.dto.user.CreateUserDTO;
import com.example.fitnesstracker.dto.user.ResponseUserDTO;

public interface UserService {
    ResponseUserDTO save(CreateUserDTO createUserDTO);
    boolean  deleteUser(String userId);
    ResponseUserDTO updateUser(Long id, CreateUserDTO updateUserDTO)throws Exception;

    ResponseUserDTO login(CreateUserDTO createUserDTO);
}


