package fitsage.mappers;

import fitsage.dto.UserDto;
import fitsage.model.User;

public class UserMapper {
    public static UserDto toDto(User user) {
        return UserDto.builder()
            .fullName(user.getFullName())
            .createdAt(user.getCreatedAt())
            .build();
    }
}

