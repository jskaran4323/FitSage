package fitsage.mappers;

import fitsage.dto.MealDto;
import fitsage.dto.UserDto;
import fitsage.model.Meal;

public class MealMapper {
    

    public static MealDto toDto(Meal entity) {
         
    UserDto owner =  new UserDto(
    entity.getUser().getFullName(),
    entity.getUser().getCreatedAt()    
    );
        return MealDto.builder()
                .id(entity.getId())
                .user(owner)
                .name(entity.getName())
                .calories(entity.getCalories())
                .protein(entity.getProtein())
                .carbs(entity.getCarbs())
                .fats(entity.getFats())
                .date(entity.getDate())
                .build();
    }
}
