package fitsage.dto.request;

import lombok.Data;


import fitsage.enums.UserType;

@Data
public class RegisterRequest {

    private String username;
    private String password;
    private String email;
    private String fullName;
    private UserType type;
  
}
