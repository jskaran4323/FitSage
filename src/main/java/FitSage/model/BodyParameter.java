package fitsage.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "body_parameters")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BodyParameter {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    private int age;
    private double height; // cm
    private double weight; // kg
    private int  gender; // M/F/Other
    private String goal;   // "Lose Weight", "Gain Muscle", etc.
    private String activityLevel; // sedentary, active, etc.
    private String dietaryPreference; // veg, non-veg, vegan
    private String allergies; // comma separated

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
