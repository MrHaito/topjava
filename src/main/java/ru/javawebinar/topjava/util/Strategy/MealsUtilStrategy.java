package ru.javawebinar.topjava.util.Strategy;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalTime;
import java.util.List;

public interface MealsUtilStrategy {
    List<MealTo> convertMealsToMealsTo(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay);
}
