package fitsage.controllers;


import fitsage.dto.WorkoutDto;
import fitsage.mappers.WorkoutMapper;
import fitsage.model.Workout;
import fitsage.services.WorkoutService;

import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<WorkoutDto>> getUserWorkouts(@PathVariable UUID userId) {
        List<Workout> workouts = workoutService.getWorkoutsByUser(userId);
         List<WorkoutDto> response = workouts.stream().map(WorkoutMapper::toDto).toList();
        return ResponseEntity.ok(response);
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