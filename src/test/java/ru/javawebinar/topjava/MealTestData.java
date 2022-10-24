package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final Meal meal_1 = new Meal(100003, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак",
            500);
    public static final Meal meal_2 = new Meal(100004, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal meal_3 = new Meal(100005, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal meal_4 = new Meal(100006, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на " +
            "граничное значение", 100);
    public static final Meal meal_5 = new Meal(100007, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак",
            1000);
    public static final Meal meal_6 = new Meal(100008, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal meal_7 = new Meal(100009, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);

    public static Meal getNewMeal() {
        return new Meal(null, LocalDateTime.of(2000, Month.JANUARY, 1, 10, 0), "New", 2000);
    }

    public static Meal getMealUpdated() {
        Meal updated = new Meal(meal_1);
        updated.setDateTime(LocalDateTime.of(2022, Month.JANUARY, 30, 10, 0));
        updated.setDescription("updatedDescription");
        updated.setCalories(2001);
        return updated;
    }

    public static void assertMealsMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMealsMatch(Iterable<Meal> actual, Meal... expected) {
        assertMealsMatch(actual, Arrays.asList(expected));
    }

    public static void assertMealsMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
