package fitsage.repositories;


import fitsage.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface MealRepository extends JpaRepository<Meal, UUID> {
    List<Meal> findByUserId(UUID userId);
    List<Meal> findByUserIdAndDate(UUID userId, LocalDate date);
    List<Meal> findBySharedTrue();
}
