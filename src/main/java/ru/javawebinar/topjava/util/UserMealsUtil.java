package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

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

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime,
                                                            LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        meals.sort(Comparator.comparing(UserMeal::getDateTime));
        List<UserMealWithExcess> list = new ArrayList<>();
        Map<LocalDate, ArrayList<UserMeal>> maps = new LinkedHashMap<>();
        ArrayList<UserMeal> userMeals = new ArrayList<>();
        for (UserMeal meal : meals) {
            LocalDate lt = LocalDate.of(meal.getDateTime().getYear(), meal.getDateTime().getMonth(),
                    meal.getDateTime().getDayOfMonth());
            if (maps.containsKey(lt) || maps.size() == 0) {
                userMeals.add(meal);
            } else {
                userMeals = new ArrayList<>();
                userMeals.add(meal);
            }
            maps.put(lt, userMeals);
        }






//        Map<LocalDate, Map<LocalDateTime, UserMeal>> maps = new LinkedHashMap<>();
//        Map<LocalDateTime, UserMeal> innerMap = new LinkedHashMap<>();
//        for (UserMeal meal : meals) {
//            LocalDate lt = LocalDate.of(meal.getDateTime().getYear(), meal.getDateTime().getMonth(),
//                    meal.getDateTime().getDayOfMonth());
//            if (maps.containsKey(lt) || maps.size() == 0) {
//                innerMap.put(meal.getDateTime(), meal);
//            } else {
//                innerMap = new LinkedHashMap<>();
//                innerMap.put(meal.getDateTime(), meal);
//            }
//            maps.put(lt, innerMap);
//        }
//
//        for (Map.Entry<LocalDate, Map<LocalDateTime, UserMeal>> entry : maps.entrySet()) {
//            int totalCaloriesPerDay = 0;
//            for (Map.Entry<LocalDateTime, UserMeal> inner : entry.getValue().entrySet()) {
//                totalCaloriesPerDay += inner.getValue().getCalories();
//            }
//            for (Map.Entry<LocalDateTime, UserMeal> inner : entry.getValue().entrySet()) {
//                UserMeal meal = inner.getValue();
//                LocalTime lt = LocalTime.of(inner.getKey().getHour(), inner.getKey().getMinute());
//                if (lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0) {
//                    list.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
//                            totalCaloriesPerDay > caloriesPerDay));
//                }
//            }
//        }
        return list;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime,
                                                             LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        return null;
    }
}
