package fitsage.dto;


import java.time.LocalDateTime;
import java.util.UUID;

import fitsage.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserDto {
    private UUID userId;
    private String fullName;
    private String username;
    private String email;
    private UserType userType;
    private LocalDateTime createdAt;
}