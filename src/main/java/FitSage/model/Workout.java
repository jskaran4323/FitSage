package fitsage.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "workouts")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Workout {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; 

    private String name;         
    private String type;        
    private int sets;
    private int reps;
    private double weight;       
    private int duration;       
    private int caloriesBurned;  

    private LocalDate date;
}
