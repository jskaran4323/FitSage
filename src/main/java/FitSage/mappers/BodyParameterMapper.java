package fitsage.mappers;

import fitsage.dto.BodyParameterDto;
import fitsage.model.BodyParameter;

public class BodyParameterMapper {

    public static BodyParameterDto toDto(BodyParameter entity) {
        return BodyParameterDto.builder()
                .userId(entity.getUser().getId())
                .age(entity.getAge())
                .height(entity.getHeight())
                .weight(entity.getWeight())
                .gender(entity.getGender())
                .goal(entity.getGoal())
                .activityLevel(entity.getActivityLevel())
                .dietaryPreference(entity.getDietaryPreference())
                .allergies(entity.getAllergies())
                .build();
    }
}
