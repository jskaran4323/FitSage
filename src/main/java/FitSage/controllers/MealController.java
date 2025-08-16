package fitsage.controllers;

import fitsage.model.Meal;
import fitsage.services.MealService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/meals")
public class MealController {

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

   
    @PostMapping("/{userId}")
    public Meal addMeal(@PathVariable UUID userId, @RequestBody Meal meal) {
        return mealService.addMeal(userId, meal);
    }

    
    @GetMapping("/{userId}")
    public List<Meal> getUserMeals(@PathVariable UUID userId) {
        return mealService.getMealsByUser(userId);
    }

    
    @GetMapping("/{userId}/date/{date}")
    public List<Meal> getUserMealsByDate(@PathVariable UUID userId, @PathVariable String date) {
        return mealService.getMealsByUserAndDate(userId, LocalDate.parse(date));
    }

    
    @DeleteMapping("/{mealId}")
    public void deleteMeal(@PathVariable UUID mealId) {
        mealService.deleteMeal(mealId);
    }
}
