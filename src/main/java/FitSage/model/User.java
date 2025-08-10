package fitsage.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

import fitsage.enums.UserType;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private int userType;

    private String password;

    private String fullName;

    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    
    public UserType getUserTypeEnum() {
        return UserType.fromValue(this.userType);
    }

    public void setUserTypeEnum(UserType type) {
        this.userType = type.getValue();
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

  
}
