package com.example.fitnesstracker.service;

import com.example.fitnesstracker.dto.workout.CreateWorkoutDTO;
import com.example.fitnesstracker.dto.workout.ResponseWorkoutDTO;
import com.example.fitnesstracker.dto.workout.UpdateWorkoutDTO;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface WorkoutService {
    ResponseWorkoutDTO save(CreateWorkoutDTO createWorkoutDTO);

    List<ResponseWorkoutDTO> findAll(LocalDate date , String type);

    ResponseWorkoutDTO update(Long id , @Valid UpdateWorkoutDTO updateWorkoutDTO);

    void delete(Long id);
}
