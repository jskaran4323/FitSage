package fitsage.controllers;

import fitsage.mappers.MealMapper;
import fitsage.mappers.WorkoutMapper;
import fitsage.model.*;
import fitsage.repositories.BodyParameterRepository;
import fitsage.services.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;
@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
public class PlanController {

    private final AIService aiService;
    private final BodyParameterRepository bodyParamRepo;

    @PostMapping("/generate/{userId}")
    public ResponseEntity<?> generatePlans(@PathVariable UUID userId) {
        BodyParameter params = bodyParamRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User profile not found"));

        Workout workoutPlan = aiService.generateWorkoutPlan(params);
        Meal mealPlan = aiService.generateMealPlan(params);

        return ResponseEntity.ok(Map.of(
                "workout", WorkoutMapper.toDto(workoutPlan),
                "meal", MealMapper.toDto(mealPlan)
        ));
    }
}
