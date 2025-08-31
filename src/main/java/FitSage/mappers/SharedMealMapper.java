package fitsage.mappers;

import fitsage.dto.SharedMealDto;
import fitsage.model.SharedMeal;

public class SharedMealMapper {

    public static SharedMealDto toDto(SharedMeal entity) {
        return SharedMealDto.builder()
                .id(entity.getId())
                .mealId(entity.getMeal() != null ? entity.getMeal().getId() : null)
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .description(entity.getDescription())
                .sharedAt(entity.getSharedAt())
                .build();
    }
}
