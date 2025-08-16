package fitsage.repositories;


import fitsage.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface WorkoutRepository extends JpaRepository<Workout, UUID> {
    List<Workout> findByUserId(UUID userId);
    List<Workout> findByUserIdAndDate(UUID userId, LocalDate date);
}