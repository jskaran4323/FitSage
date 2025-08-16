package fitsage.impl;

import org.springframework.stereotype.Service;

import fitsage.model.User;
import fitsage.model.Meal;
import fitsage.repositories.MealRepository;
import fitsage.services.MealService;
import fitsage.repositories.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MealServiceImpl implements MealService {

    private final MealRepository mealRepository;
    private final UserRepository userRepository;

    public MealServiceImpl(MealRepository mealRepository, UserRepository userRepository) {
        this.mealRepository = mealRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Meal addMeal(UUID userId, Meal meal) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        meal.setUser(userOpt.get());
        if (meal.getDate() == null) {
            meal.setDate(LocalDate.now());
        }
        return mealRepository.save(meal);
    }

    @Override
    public List<Meal> getMealsByUser(UUID userId) {
        return mealRepository.findByUserId(userId);
    }

    @Override
    public List<Meal> getMealsByUserAndDate(UUID userId, LocalDate date) {
        return mealRepository.findByUserIdAndDate(userId, date);
    }

    @Override
    public void deleteMeal(UUID mealId) {
        mealRepository.deleteById(mealId);
    }
}
