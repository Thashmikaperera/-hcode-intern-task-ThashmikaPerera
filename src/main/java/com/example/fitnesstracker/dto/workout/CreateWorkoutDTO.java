package com.example.fitnesstracker.dto.workout;

import com.example.fitnesstracker.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateWorkoutDTO implements Serializable {

    @NotNull(message = "User is required")
    private Long userId;

    @NotEmpty(message = "Type is required")
    private String type;

    @NotNull(message = "Duration is required")
    private Double duration;

    @NotNull(message = "Calories is required")
    private Double calories;

    @NotNull(message = "Date is required")
    private LocalDate date;
}
