package com.example.fitnesstracker.dto.workout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseWorkoutDTO implements Serializable {
    private Long id;
    private Long userId;
    private String type;
    private Double duration;
    private Double calories;
    private LocalDate date;
}
