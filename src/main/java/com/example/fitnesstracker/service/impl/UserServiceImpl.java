package com.example.fitnesstracker.service.impl;

import com.example.fitnesstracker.dto.user.CreateUserDTO;
import com.example.fitnesstracker.dto.user.ResponseUserDTO;
import com.example.fitnesstracker.entity.User;
import com.example.fitnesstracker.repository.UserRepository;
import com.example.fitnesstracker.service.UserService;
import com.example.fitnesstracker.util.exception.CustomException;
import com.example.fitnesstracker.util.map.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Mapper mapper;
    private final ModelMapper modelMapper;

    @Override
    public ResponseUserDTO save(CreateUserDTO createUserDTO) {
        if (createUserDTO == null) {
            throw new CustomException("CreateUserDTO cannot be null");
        }

        User user = mapper.map(createUserDTO, User.class);
        User savedUser = userRepository.save(user);

        if (savedUser == null ) {
            throw new CustomException("User cannot be saved");
        }

        return mapper.map(savedUser, ResponseUserDTO.class);
    }
    @Override
    public boolean  deleteUser(String userId) {
        long id = Long.parseLong(userId);
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
    public ResponseUserDTO updateUser(Long id, CreateUserDTO updateUserDTO) throws Exception {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUsername(updateUserDTO.getUsername());
            user.setEmail(updateUserDTO.getEmail());
            user.setPassword(updateUserDTO.getPassword());

            User updatedUser = userRepository.save(user);

            // Convert entity to DTO
            ResponseUserDTO responseUserDTO = modelMapper.map(updatedUser,ResponseUserDTO.class);
            return responseUserDTO;
        } else {
            throw new Exception("User not found with ID: " + id);
        }
    }

    @Override
    public ResponseUserDTO login(CreateUserDTO createUserDTO) {
        Optional<User> byEmail = userRepository.findByEmail(createUserDTO.getEmail());
        if (byEmail.isPresent()) {
            User user = byEmail.get();
            return mapper.map(user, ResponseUserDTO.class);
        } else {
            return null;
        }

    }
}
