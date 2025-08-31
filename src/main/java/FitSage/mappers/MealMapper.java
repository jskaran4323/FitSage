package fitsage.mappers;

import fitsage.dto.MealDto;
import fitsage.model.Meal;

public class MealMapper {

    public static MealDto toDto(Meal entity) {
        return MealDto.builder()
                .id(entity.getId())
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .name(entity.getName())
                .calories(entity.getCalories())
                .protein(entity.getProtein())
                .carbs(entity.getCarbs())
                .fats(entity.getFats())
                .date(entity.getDate())
                .build();
    }
}
