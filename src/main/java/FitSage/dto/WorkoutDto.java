package fitsage.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WorkoutDto {
    private UUID id;
    private UserDto user;
    private String name;
    private String type;
    private int sets;
    private int reps;
    private double weight;
    private int duration;
    private int caloriesBurned;
    private LocalDate date;
}
