package ru.javawebinar.topjava.util.Strategy;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConvertToMealTo implements MealsUtilStrategy {
    @Override
    public List<MealTo> convertMealsToMealsTo(List<Meal> meals, LocalTime startTime, LocalTime endTime,
                                              int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(Collectors.groupingBy(Meal::getDate,Collectors.summingInt(Meal::getCalories)));
        return meals.stream()
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getUuid(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
