package fitsage.mappers;

import fitsage.dto.WorkoutDto;
import fitsage.model.Workout;

public class WorkoutMapper {

    public static WorkoutDto toDto(Workout entity) {
        return WorkoutDto.builder()
                .id(entity.getId())
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .name(entity.getName())
                .type(entity.getType())
                .sets(entity.getSets())
                .reps(entity.getReps())
                .weight(entity.getWeight())
                .duration(entity.getDuration())
                .caloriesBurned(entity.getCaloriesBurned())
                .date(entity.getDate())
                .build();
    }
}
