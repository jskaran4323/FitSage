package fitsage.impl;




import org.springframework.stereotype.Service;

import fitsage.model.User;
import fitsage.model.Workout;
import fitsage.repositories.UserRepository;
import fitsage.repositories.WorkoutRepository;
import fitsage.services.WorkoutService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;

    public WorkoutServiceImpl(WorkoutRepository workoutRepository, UserRepository userRepository) {
        this.workoutRepository = workoutRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Workout addWorkout(UUID userId, Workout workout) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        workout.setUser(userOpt.get());
        if (workout.getDate() == null) {
            workout.setDate(LocalDate.now());
        }
        return workoutRepository.save(workout);
    }

    @Override
    public List<Workout> getWorkoutsByUser(UUID userId) {
        return workoutRepository.findByUserId(userId);
    }

    @Override
    public List<Workout> getWorkoutsByUserAndDate(UUID userId, LocalDate date) {
        return workoutRepository.findByUserIdAndDate(userId, date);
    }

    @Override
    public void deleteWorkout(UUID workoutId) {
        workoutRepository.deleteById(workoutId);
    }

  
}

