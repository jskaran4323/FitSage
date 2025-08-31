package fitsage.services;

import fitsage.model.BodyParameter;
import fitsage.model.Meal;
import fitsage.model.Workout;

public interface AIService {
    Workout generateWorkoutPlan(BodyParameter params);
    Meal generateMealPlan(BodyParameter params);
}
