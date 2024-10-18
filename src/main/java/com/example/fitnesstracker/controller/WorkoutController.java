package com.example.fitnesstracker.controller;

import com.example.fitnesstracker.dto.workout.CreateWorkoutDTO;
import com.example.fitnesstracker.dto.workout.ResponseWorkoutDTO;
import com.example.fitnesstracker.dto.workout.UpdateWorkoutDTO;
import com.example.fitnesstracker.service.WorkoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/workouts")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class WorkoutController {

    private final WorkoutService workoutService;

    @GetMapping("/health")
    public String health() {
        return "Workout Controller is healthy";
    }

    @PostMapping()
    public ResponseEntity<?> save(@Valid @RequestBody CreateWorkoutDTO createWorkoutDTO, BindingResult bindingResult) {

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
            ResponseWorkoutDTO responseWorkoutDTO = workoutService.save(createWorkoutDTO);
            return ResponseEntity.ok(responseWorkoutDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }


    }

    @GetMapping()
    public ResponseEntity<?> findAll(
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) String type
    ) {
        return ResponseEntity.ok(workoutService.findAll(date, type));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody UpdateWorkoutDTO updateWorkoutDTO, BindingResult bindingResult) {
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
            ResponseWorkoutDTO responseWorkoutDTO =  workoutService.update(id, updateWorkoutDTO);
            return ResponseEntity.ok(responseWorkoutDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            workoutService.delete(id);
            return ResponseEntity.ok("Workout deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }


}
