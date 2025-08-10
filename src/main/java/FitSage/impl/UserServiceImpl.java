package fitsage.impl;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fitsage.dto.request.RegisterRequest;
import fitsage.enums.UserType;
import fitsage.model.User;
import fitsage.repositories.UserRepository;
import fitsage.services.UserService;
@Service
public class UserServiceImpl implements UserService{
     @Autowired
     private  UserRepository userRepository;
     private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

     public User registerUser(RegisterRequest request){
        if (userRepository.findByUsername(request.getUsername()).isPresent()){
            throw new RuntimeException("Username already exists");
        }

        UserType userType = request.getType();
        System.out.println(userType);
        if (userType == null) {
            userType = UserType.VIEWER;
        }
        if (userType == UserType.ADMIN || userType == UserType.MANAGER) {
            throw new IllegalArgumentException("Cannot assign reserved roles.");
        }
    
              
              User user = User.builder()
              .username(request.getUsername()).
              password(passwordEncoder.encode(request.getPassword())).fullName(request.getFullName()).email(request.getEmail()).userType(userType.getValue()).build();
           
              return userRepository.save(user);

    }

    @Override
    public List<User> getUser() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }

    @Override
     public User updateUser(UUID userId, RegisterRequest request) {
        return userRepository.findById(userId).map(user -> {
            user.setUsername(request.getUsername());
            user.setFullName(request.getFullName());
            user.setEmail(request.getEmail());
    
            if (request.getPassword() != null && !request.getPassword().isBlank()) {
                user.setPassword(passwordEncoder.encode(request.getPassword()));
            }
    
            if (request.getType() != null) {
                user.setUserType(request.getType().getValue());
            }
    
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    @Override
    public Optional<User> userById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUsernameOrEmail(String identifier) {
        return userRepository.findByUsername(identifier)
                .or(() -> userRepository.findByEmail(identifier));
    }

    @Override
    public List<User> getUserByType(int type) {
        return userRepository.findByUserType(type);
    }

    @Override
    public User getRequiredById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
    }

    @Override
    public User getRequiredByUsernameOrEmail(String identifier) {
        return findByUsernameOrEmail(identifier)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with username or email: " + identifier));
    }
}
