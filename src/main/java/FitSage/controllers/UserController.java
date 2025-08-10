package fitsage.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import fitsage.dto.UserDto;
import fitsage.mappers.UserMapper;
import fitsage.model.User;
import fitsage.services.UserService;

import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
public UserDto getMyDetails(Authentication auth) {
    User user = (User) auth.getPrincipal();
    
    return UserMapper.toDto(user);
}
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public List<UserDto> getUsers() {
        return userService.getUser()
                          .stream()
                          .map(UserMapper::toDto)
                          .toList();
    }
}
