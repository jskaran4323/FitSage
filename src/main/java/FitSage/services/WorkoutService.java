package fitsage.services;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import fitsage.dto.MealDto;
import fitsage.dto.WorkoutDto;
import fitsage.model.Workout;

public interface WorkoutService {
    Workout addWorkout(UUID userId, Workout workoutDto);
    List<Workout> getWorkoutsByUser(UUID userId);
    List<Workout> getWorkoutsByUserAndDate(UUID userId, LocalDate date);
    void deleteWorkout(UUID workoutId);
    WorkoutDto shareWorkout(UUID workoutId, boolean shared);
    List<WorkoutDto> getSharedWorkout();
}

