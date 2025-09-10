package fitsage.controllers;

import fitsage.mappers.SharedMealMapper;
import fitsage.mappers.SharedWorkoutMapper;
import fitsage.model.*;
import fitsage.repositories.SharedMealRepository;
import fitsage.repositories.SharedWorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;
@RestController
@RequestMapping("api/community")
@RequiredArgsConstructor
public class CommunityController {

    private final SharedWorkoutRepository sharedWorkoutRepo;
    private final SharedMealRepository sharedMealRepo;

    // ---- Workouts ----
    @PostMapping("/share/workout/{workoutId}")
    public ResponseEntity<?> shareWorkout(@PathVariable UUID workoutId, @RequestParam UUID userId) {
        SharedWorkout sw = SharedWorkout.builder()
                .user(User.builder().id(userId).build())
                .workout(Workout.builder().id(workoutId).build())
                .description("Shared workout")
                .sharedAt(LocalDateTime.now())
                .build();

        sharedWorkoutRepo.save(sw);
        return ResponseEntity.ok(SharedWorkoutMapper.toDto(sw));
    }

    @GetMapping("/workouts")
    public ResponseEntity<?> getAllSharedWorkouts() {
        return ResponseEntity.ok(
            sharedWorkoutRepo.findAll()
                             .stream()
                             .map(SharedWorkoutMapper::toDto)
                             .toList()
        );
    }

    // ---- Meals ----
    @PostMapping("/share/meal/{mealId}")
    public ResponseEntity<?> shareMeal(@PathVariable UUID mealId, @RequestParam UUID userId) {
        SharedMeal sm = SharedMeal.builder()
                .user(User.builder().id(userId).build())
                .meal(Meal.builder().id(mealId).build())
                .description("Shared meal")
                .sharedAt(LocalDateTime.now())
                .build();

        sharedMealRepo.save(sm);
        return ResponseEntity.ok(SharedMealMapper.toDto(sm));
    }

    @GetMapping("/meals")
    public ResponseEntity<?> getAllSharedMeals() {
        return ResponseEntity.ok(
            sharedMealRepo.findAll()
                          .stream()
                          .map(SharedMealMapper::toDto)
                          .toList()
        );
    }
}
