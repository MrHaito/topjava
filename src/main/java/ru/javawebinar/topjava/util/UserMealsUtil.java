package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак"
                , 500), new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 510));

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime,
                                                            LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        List<UserMealWithExcess> list = new ArrayList<>();
        Map<LocalDate, Integer> totalCaloriesPerDate = new LinkedHashMap<>();
        for (UserMeal meal : meals) {
            LocalDate ld = meal.getDateTime().toLocalDate();
            totalCaloriesPerDate.merge(ld, meal.getCalories(), Integer::sum);
        }
        for (UserMeal meal : meals) {
            LocalTime lt = meal.getDateTime().toLocalTime();
            if (lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0) {
                list.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        totalCaloriesPerDate.getOrDefault(meal.getDateTime().toLocalDate(), 0) > caloriesPerDay));
            }
        }
        return list;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime,
                                                             LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> totalCaloriesPerDate =
                meals.stream().collect(Collectors.toMap(e -> e.getDateTime().toLocalDate(), UserMeal::getCalories,
                        Integer::sum));
        return meals.stream().filter(lt -> lt.getDateTime().toLocalTime().compareTo(startTime) >= 0 &&
                lt.getDateTime().toLocalTime().compareTo(endTime) < 0)
                .map(p -> new UserMealWithExcess(p.getDateTime(), p.getDescription(), p.getCalories(),
                        totalCaloriesPerDate.getOrDefault(p.getDateTime().toLocalDate(), 0) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
