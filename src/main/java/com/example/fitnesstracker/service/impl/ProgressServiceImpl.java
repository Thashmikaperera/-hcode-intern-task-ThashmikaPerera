package com.example.fitnesstracker.service.impl;

import com.example.fitnesstracker.dto.progress.CreateProgressDTO;
import com.example.fitnesstracker.dto.progress.ResponseProgressDTO;
import com.example.fitnesstracker.dto.user.ResponseUserDTO;
import com.example.fitnesstracker.dto.workout.ResponseWorkoutDTO;
import com.example.fitnesstracker.entity.Progress;
import com.example.fitnesstracker.entity.User;
import com.example.fitnesstracker.entity.Workout;
import com.example.fitnesstracker.repository.ProgressRepository;
import com.example.fitnesstracker.repository.UserRepository;
import com.example.fitnesstracker.service.ProgressService;
import com.example.fitnesstracker.util.exception.CustomException;
import com.example.fitnesstracker.util.map.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProgressServiceImpl implements ProgressService {

    private final ProgressRepository progressRepository;
    private final UserRepository userRepository;
    private final Mapper mapper;

    @Override
    public ResponseProgressDTO save(CreateProgressDTO createProgressDTO) {
        if (createProgressDTO == null) {
            throw new CustomException("Progress cannot be null");
        }



        if (!userRepository.existsById(createProgressDTO.getUserID())) {
            throw new CustomException("User not found");
        }

        return mapper.map(progressRepository.save(mapper.map(createProgressDTO, Progress.class)), ResponseProgressDTO.class);



    }

    @Override
    public boolean deleteProgress(String progressId) {
        long id = Long.parseLong(progressId);
        Optional<Progress> progress = progressRepository.findById(id);
        if (progress.isPresent()) {
            progressRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ResponseProgressDTO updateProgress(Long id, CreateProgressDTO updateProgressDTO) throws Exception {
        if (updateProgressDTO == null) {
            throw new CustomException("UpdateProgressDTO cannot be null");
        }

        if (id == null) {
            throw new CustomException("Progress id cannot be null");
        }

        Progress progress = progressRepository.findById(id)
                .orElseThrow(() -> new CustomException("Progress not found with id: " + id));


        if (updateProgressDTO.getGoal() != null) {
            progress.setGoal(updateProgressDTO.getGoal());
        }

        if (updateProgressDTO.getWeight() != null) {
            progress.setWeight(updateProgressDTO.getWeight());
        }

        if (updateProgressDTO.getDate() != null) {
            progress.setDate(updateProgressDTO.getDate());
        }

        return mapper.map(progressRepository.save(progress), ResponseProgressDTO.class);
    }

    @Override
    public List<ResponseProgressDTO> findAll(Double weight , String goal) {

        List<Progress> progresses = progressRepository.findAll();


        List<ResponseProgressDTO> responseProgressDTOS = new ArrayList<>();

        for (Progress progress : progresses) {


            progress.setUser(userRepository.findById(progress.getUser().getId())
                    .orElseThrow(() -> new CustomException("User not found with id: " + progress.getUser().getId())));


            boolean matchesWeight = (weight == null || progress.getWeight().equals(weight));
            boolean matchesGoal = (goal == null || progress.getGoal().toLowerCase().contains(goal.toLowerCase()));

            if (matchesWeight && matchesGoal) {

                responseProgressDTOS.add(mapper.map(progress, ResponseProgressDTO.class));
            }
        }

        return responseProgressDTOS;
    }
}
