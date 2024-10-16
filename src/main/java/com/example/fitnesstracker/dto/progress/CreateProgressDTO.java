package com.example.fitnesstracker.dto.progress;

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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateProgressDTO implements Serializable {

    @NotNull(message = "Weight is required")
    private Double weight;
    @NotEmpty(message = "Goal is required")
    private String goal;
    @NotNull(message = "Date is required")
    private LocalDate date;
    @NotNull(message = "User ID is required")
    private Long userID;

}
