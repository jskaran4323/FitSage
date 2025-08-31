package fitsage.services;

import java.util.List;

import fitsage.model.BodyParameter;
import fitsage.model.Meal;
import fitsage.model.Workout;

public interface AIService {
    List<Workout> generateWorkoutPlan(BodyParameter params);
    List<Meal> generateMealPlan(BodyParameter params);
}
