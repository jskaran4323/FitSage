package fitsage.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "shared_meals")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SharedMeal {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Meal meal;

    private String description;
    private LocalDateTime sharedAt;
}
