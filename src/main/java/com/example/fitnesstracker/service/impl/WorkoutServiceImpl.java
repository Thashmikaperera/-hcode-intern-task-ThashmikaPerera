package com.example.fitnesstracker.service.impl;

import com.example.fitnesstracker.dto.workout.CreateWorkoutDTO;
import com.example.fitnesstracker.dto.workout.ResponseWorkoutDTO;
import com.example.fitnesstracker.dto.workout.UpdateWorkoutDTO;
import com.example.fitnesstracker.entity.User;
import com.example.fitnesstracker.entity.Workout;
import com.example.fitnesstracker.repository.UserRepository;
import com.example.fitnesstracker.repository.WorkoutRepository;
import com.example.fitnesstracker.service.WorkoutService;
import com.example.fitnesstracker.util.exception.CustomException;
import com.example.fitnesstracker.util.map.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;
    private final Mapper mapper;

    @Override
    public ResponseWorkoutDTO save(CreateWorkoutDTO createWorkoutDTO) {
        if (createWorkoutDTO == null) {
            throw new CustomException("CreateWorkoutDTO cannot be null");
        }

        if (createWorkoutDTO.getUserId() == null) {
            throw new CustomException("UserId cannot be null");
        }

        User user = userRepository.findById(createWorkoutDTO.getUserId())
                .orElseThrow(() -> new CustomException("User not found with id: " + createWorkoutDTO.getUserId()));

        Workout workout = mapper.map(createWorkoutDTO, Workout.class);
        workout.setUser(user);

        return mapper.map(workoutRepository.save(workout), ResponseWorkoutDTO.class);
    }

    @Override
    public List<ResponseWorkoutDTO> findAll(LocalDate date, String type) {

        // Fetch all workouts from the repository
        List<Workout> workouts = workoutRepository.findAll();

        // Create a list to store the filtered results
        List<ResponseWorkoutDTO> responseWorkoutDTOS = new ArrayList<>();

        for (Workout workout : workouts) {

            // Lazy-load the associated user
            workout.setUser(userRepository.findById(workout.getUser().getId())
                    .orElseThrow(() -> new CustomException("User not found with id: " + workout.getUser().getId())));

            // Filter by date, type, or both
            boolean matchesDate = (date == null || workout.getDate().equals(date));
            boolean matchesType = (type == null || workout.getType().toLowerCase().contains(type.toLowerCase()));

            if (matchesDate && matchesType) {
                // Add to the response list if both conditions are met
                responseWorkoutDTOS.add(mapper.map(workout, ResponseWorkoutDTO.class));
            }
        }

        return responseWorkoutDTOS;
    }

    @Override
    public ResponseWorkoutDTO update(Long id , UpdateWorkoutDTO updateWorkoutDTO) {
        if (updateWorkoutDTO == null) {
            throw new CustomException("UpdateWorkoutDTO cannot be null");
        }

        if (id == null) {
            throw new CustomException("Workout id cannot be null");
        }

        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new CustomException("Workout not found with id: " + id));


        if (updateWorkoutDTO.getType() != null) {
            workout.setType(updateWorkoutDTO.getType());
        }

        if (updateWorkoutDTO.getDuration() != null) {
            workout.setDuration(updateWorkoutDTO.getDuration());
        }

        if (updateWorkoutDTO.getCalories() != null) {
            workout.setCalories(updateWorkoutDTO.getCalories());
        }

        if (updateWorkoutDTO.getDate() != null) {
            workout.setDate(updateWorkoutDTO.getDate());
        }

        return mapper.map(workoutRepository.save(workout), ResponseWorkoutDTO.class);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new CustomException("Workout id cannot be null");
        }

        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new CustomException("Workout not found with id: " + id));

        workoutRepository.delete(workout);
    }

}