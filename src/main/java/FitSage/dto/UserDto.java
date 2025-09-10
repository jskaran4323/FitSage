package fitsage.dto;


import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.cglib.core.Local;

import fitsage.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserDto {
    private String fullName;
    private LocalDateTime createdAt; 
   
}