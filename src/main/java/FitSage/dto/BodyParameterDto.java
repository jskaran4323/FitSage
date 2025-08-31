package fitsage.dto;

import lombok.*;

import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BodyParameterDto {
    private UUID userId;
    private int age;
    private double height;
    private double weight;
    private int gender; // 0 = male, 1 = female
    private String goal;
    private String activityLevel;
    private String dietaryPreference;
    private String allergies;
}
