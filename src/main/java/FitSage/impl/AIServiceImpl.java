package fitsage.impl;

import fitsage.model.*;
import fitsage.services.AIService;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AIServiceImpl implements AIService {

    @Override
    public Workout generateWorkoutPlan(BodyParameter params) {
        // Simple stubbed logic – later replace with real AI
        return Workout.builder()
                .name(params.getGoal() + " Workout Plan")
                .type("Strength & Cardio")
                .sets(4)
                .reps(12)
                .weight(20.0)
                .duration(60) // in minutes
                .caloriesBurned(400)
                .date(LocalDate.now())
                .build();
    }

    @Override
    public Meal generateMealPlan(BodyParameter params) {
        // Basic calculation: calories ~ BMR range (dummy numbers here)
        int baseCalories = switch (params.getGoal().toLowerCase()) {
            case "lose weight" -> 1800;
            case "gain muscle" -> 2500;
            default -> 2200;
        };

        // Randomize macros slightly to simulate variety
        double protein = ThreadLocalRandom.current().nextDouble(100, 150);
        double carbs   = ThreadLocalRandom.current().nextDouble(200, 300);
        double fats    = ThreadLocalRandom.current().nextDouble(50, 80);

        return Meal.builder()
                .name(params.getGoal() + " Meal Plan")
                .calories(baseCalories)
                .protein(protein)
                .carbs(carbs)
                .fats(fats)
                .date(LocalDate.now())
                .build();
    }
}
