package com.example.fitnesstracker.controller;

import com.example.fitnesstracker.dto.progress.CreateProgressDTO;
import com.example.fitnesstracker.dto.progress.ResponseProgressDTO;
import com.example.fitnesstracker.dto.user.CreateUserDTO;
import com.example.fitnesstracker.dto.user.ResponseUserDTO;
import com.example.fitnesstracker.service.ProgressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/progress")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ProgressController {

    private final ProgressService progressService;

    @GetMapping("/health")
    public String health() {
        return "Progress Controller is healthy";
    }

    @PostMapping("/add")
    public ResponseEntity<?> save(@Valid @RequestBody CreateProgressDTO createProgressDTO , BindingResult bindingResult){
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
            ResponseProgressDTO responseProgressDTO = progressService.save(createProgressDTO);
            return ResponseEntity.ok(responseProgressDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //delete
    @DeleteMapping("/progressDelete/{id}")
    public ResponseEntity<?> deleteProgress(@PathVariable String id) {
        try {
            progressService.deleteProgress(id);
            return ResponseEntity.ok("Progress deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProgress(@PathVariable Long id, @Valid @RequestBody CreateProgressDTO updateProgressDTO, BindingResult bindingResult) {
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
            ResponseProgressDTO updateProgress = progressService.updateProgress(id, updateProgressDTO);
            return ResponseEntity.ok(updateProgress);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<?> findAll(
            @RequestParam(required = false) Double weight,
            @RequestParam(required = false) String goal
    ) {
        return ResponseEntity.ok(progressService.findAll(weight, goal));
    }
}
