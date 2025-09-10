package fitsage.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "shared_workouts")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SharedWorkout {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Workout workout;

    private String description;
    private LocalDateTime sharedAt;
}
