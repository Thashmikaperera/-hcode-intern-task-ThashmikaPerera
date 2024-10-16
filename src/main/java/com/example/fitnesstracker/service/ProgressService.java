package com.example.fitnesstracker.service;

import com.example.fitnesstracker.dto.progress.CreateProgressDTO;
import com.example.fitnesstracker.dto.progress.ResponseProgressDTO;
import com.example.fitnesstracker.dto.user.CreateUserDTO;
import com.example.fitnesstracker.dto.user.ResponseUserDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface ProgressService {
    ResponseProgressDTO save(@Valid CreateProgressDTO createProgressDTO);
    boolean  deleteProgress(String userId);
    ResponseProgressDTO updateProgress(Long id, CreateProgressDTO updateProgressDTO)throws Exception;
    List<ResponseProgressDTO> findAll(Double weight , String goal);
}
