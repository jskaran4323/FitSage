package fitsage.services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import fitsage.model.Meal;

public interface MealService {
    Meal addMeal(UUID userId, Meal meal);
    List<Meal> getMealsByUser(UUID userId);
    List<Meal> getMealsByUserAndDate(UUID userId, LocalDate date);
    void deleteMeal(UUID mealId);
}
