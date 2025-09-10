package fitsage.controllers;

import fitsage.dto.MealDto;
import fitsage.dto.WorkoutDto;
import fitsage.mappers.MealMapper;
import fitsage.model.Meal;
import fitsage.services.MealService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<MealDto>> getUserMeals(@PathVariable UUID userId) {
        List<Meal> meals =  mealService.getMealsByUser(userId);
        List<MealDto> response  = meals.stream().map(MealMapper::toDto).toList();
        return ResponseEntity.ok(response);
    }

    
    @GetMapping("/{userId}/date/{date}")
    public List<Meal> getUserMealsByDate(@PathVariable UUID userId, @PathVariable String date) {
        return mealService.getMealsByUserAndDate(userId, LocalDate.parse(date));
    }

    
    @DeleteMapping("/{mealId}")
    public void deleteMeal(@PathVariable UUID mealId) {
        mealService.deleteMeal(mealId);
    }

    @GetMapping("/shared")
    public  ResponseEntity<List<MealDto>> getSharedMeals () {
        return ResponseEntity.ok(mealService.getSharedMeals());
        
    }
    @PostMapping("/{mealId}/share")
public ResponseEntity<MealDto> sharemeal(
        @PathVariable UUID mealId,
        @RequestParam boolean shared
) {
    return ResponseEntity.ok(mealService.shareMeal(mealId, shared));
}

    
}
