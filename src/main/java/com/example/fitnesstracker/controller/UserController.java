package com.example.fitnesstracker.controller;

import com.example.fitnesstracker.dto.user.CreateUserDTO;
import com.example.fitnesstracker.dto.user.ResponseUserDTO;
import com.example.fitnesstracker.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    @GetMapping("/health")
    public String health() {
        return "User Controller is healthy";
    }

    @PostMapping("/signup")
    public ResponseEntity<?> save(@Valid @RequestBody CreateUserDTO createUserDTO , BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = bindingResult
                    .getAllErrors()
                    .stream()
                    .collect(Collectors.toMap(
                            error -> ((org.springframework.validation.FieldError) error).getField() ,
                            error -> error.getDefaultMessage()
                    ));

            return ResponseEntity.badRequest()
                    .body(errorMap);
        }
        try {

            ResponseUserDTO responseUserDTO = userService.save(createUserDTO);
            return ResponseEntity.ok(responseUserDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }
    @GetMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody CreateUserDTO createUserDTO, BindingResult bindingResult) {
        ResponseUserDTO login = userService.login(createUserDTO);
        if (login ==null) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }else {
            return ResponseEntity.ok(login);
        }
    }
    //delete
    @DeleteMapping("/userDelete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                   .body(e.getMessage());
        }
    }
    //update
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody CreateUserDTO updateUserDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = bindingResult.getAllErrors()
                    .stream()
                    .collect(Collectors.toMap(
                            error -> ((FieldError) error).getField(),
                            error -> error.getDefaultMessage()
                    ));

            return ResponseEntity.badRequest().body(errorMap);
        }

        try {
            ResponseUserDTO updatedUser = userService.updateUser(id, updateUserDTO);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
