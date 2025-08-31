package fitsage.mappers;

import fitsage.dto.SharedWorkoutDto;
import fitsage.model.SharedWorkout;

public class SharedWorkoutMapper {

    public static SharedWorkoutDto toDto(SharedWorkout entity) {
        return SharedWorkoutDto.builder()
                .id(entity.getId())
                .workoutId(entity.getWorkout() != null ? entity.getWorkout().getId() : null)
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .description(entity.getDescription())
                .sharedAt(entity.getSharedAt())
                .build();
    }
}
