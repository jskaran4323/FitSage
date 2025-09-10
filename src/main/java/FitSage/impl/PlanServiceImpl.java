package fitsage.impl;

import fitsage.model.BodyParameter;
import fitsage.model.Meal;
import fitsage.model.User;
import fitsage.model.Workout;
import fitsage.repositories.BodyParameterRepository;
import fitsage.repositories.MealRepository;
import fitsage.repositories.UserRepository;
import fitsage.repositories.WorkoutRepository;
import fitsage.services.AIService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class PlanServiceImpl {

    @Autowired
    private AIService aiService;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private WorkoutRepository workoutRepo;

    @Autowired
    private MealRepository mealRepo;

    @Autowired
    private BodyParameterRepository bodyParamRepo;

    public Map<String, Object> generatePlans(UUID userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        BodyParameter params = bodyParamRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Body parameters not set"));

        // Generate from AI
        List<Workout> workouts = aiService.generateWorkoutPlan(params);
        List<Meal> meals = aiService.generateMealPlan(params);

           workouts.forEach(w -> {
        w.setUser(user);
        w.setDate(LocalDate.now());
        workoutRepo.save(w);
    });

    meals.forEach(m -> {
        m.setUser(user);
        m.setDate(LocalDate.now());
        mealRepo.save(m);
    });

        return Map.of(
                "workout", workouts,
                "meal", meals
        );
    }
}
