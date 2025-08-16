package fitsage.controllers;


import fitsage.model.Workout;
import fitsage.services.WorkoutService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

  
    @PostMapping("/{userId}")
    public Workout addWorkout(@PathVariable UUID userId, @RequestBody Workout workout) {
        return workoutService.addWorkout(userId, workout);
    }

    
    @GetMapping("/{userId}")
    public List<Workout> getUserWorkouts(@PathVariable UUID userId) {
        return workoutService.getWorkoutsByUser(userId);
    }

    
    @GetMapping("/{userId}/date/{date}")
    public List<Workout> getUserWorkoutsByDate(@PathVariable UUID userId, @PathVariable String date) {
        return workoutService.getWorkoutsByUserAndDate(userId, LocalDate.parse(date));
    }

   
    @DeleteMapping("/{workoutId}")
    public void deleteWorkout(@PathVariable UUID workoutId) {
        workoutService.deleteWorkout(workoutId);
    }
}