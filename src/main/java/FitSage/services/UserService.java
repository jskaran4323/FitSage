// fitsage/service/UserService.java
package fitsage.services;

import fitsage.dto.request.RegisterRequest;
import fitsage.model.User; // your entity


import java.util.*;

public interface UserService{
        User registerUser(RegisterRequest request);
        List<User> getUser();
        void deleteUser(UUID userId);
        User updateUser(UUID userId, RegisterRequest request);
    
        
        Optional<User> userById(UUID id);
        Optional<User> findByUsername(String username);
        Optional<User> findByEmail(String email);
        Optional<User> findByUsernameOrEmail(String identifier);
        List<User> getUserByType(int type);
    
        
        User getRequiredById(UUID id);
        User getRequiredByUsernameOrEmail(String identifier);
    
    
}
