package fitsage.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MealDto {
    private UUID id;
    private UserDto user;
    private String name;
    private int calories;
    private double protein;
    private double carbs;
    private double fats;
    private LocalDate date;
}
