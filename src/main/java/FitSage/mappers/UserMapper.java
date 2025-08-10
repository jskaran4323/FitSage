package fitsage.mappers;

import fitsage.dto.UserDto;
import fitsage.model.User;

public class UserMapper {
    public static UserDto toDto(User user) {
        return UserDto.builder()
            .userId(user.getId())
            .fullName(user.getFullName())
            .username(user.getUsername())
            .email(user.getEmail())
            .userType(user.getUserTypeEnum())
            .createdAt(user.getCreatedAt())
            .build();
    }
}

