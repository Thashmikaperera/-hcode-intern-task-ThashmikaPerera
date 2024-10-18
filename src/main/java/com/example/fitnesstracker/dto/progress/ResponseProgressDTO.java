package com.example.fitnesstracker.dto.progress;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseProgressDTO {

    private Long id;
    private Double weight;
    private String goal;
    private Date date;
    private Long userID;
}
