package fitsage.mappers;

import fitsage.dto.UserDto;
import fitsage.dto.WorkoutDto;
import fitsage.model.Workout;

public class WorkoutMapper {

    public static WorkoutDto toDto(Workout entity) {
      
        UserDto owner = new UserDto(
                entity.getUser().getFullName(),
                entity.getUser().getCreatedAt()
        );
        return WorkoutDto.builder()
                .id(entity.getId())
                .user(owner)
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
