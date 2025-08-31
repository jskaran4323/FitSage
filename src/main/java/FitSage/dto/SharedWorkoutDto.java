package fitsage.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SharedWorkoutDto {
    private UUID id;
    private UUID workoutId;
    private UUID userId;
    private String description;
    private LocalDateTime sharedAt;
}
