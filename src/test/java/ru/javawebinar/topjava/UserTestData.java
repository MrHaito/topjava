package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int GUEST_ID = START_SEQ + 2;
    public static final int NOT_FOUND = 10;

    public static final User user = new User(USER_ID, "User", "user@yandex.ru", "password", Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN);
    public static final User guest = new User(GUEST_ID, "Guest", "guest@gmail.com", "guest");

    public static final Meal MEAL_1 = new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак",
            500);
    public static final Meal MEAL_2 = new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL_3 = new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal MEAL_4 = new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на " +
            "граничное значение", 100);
    public static final Meal MEAL_5 = new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак",
            1000);
    public static final Meal MEAL_6 = new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal MEAL_7 = new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);

    public static User getNewUser() {
        return new User(null, "New", "new@gmail.com", "newPass", 1555, false, new Date(), Collections.singleton(Role.USER));
    }

    public static Meal getNewMeal() {
        return new Meal(null, LocalDateTime.of(2000, Month.JANUARY, 1, 10, 0), "New", 2000);
    }

    public static User getUserUpdated() {
        User updated = new User(user);
        updated.setEmail("update@gmail.com");
        updated.setName("UpdatedName");
        updated.setCaloriesPerDay(330);
        updated.setPassword("newPass");
        updated.setEnabled(false);
        updated.setRoles(Collections.singletonList(Role.ADMIN));
        return updated;
    }

    public static Meal getMealUpdated() {
        Meal updated = new Meal(MEAL_1);
        updated.setDateTime(LocalDateTime.of(2022, Month.JANUARY, 30, 10, 0));
        updated.setDescription("updatedDescription");
        updated.setCalories(2001);
        return updated;
    }

    public static void assertUserMatch(User actual, User expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("registered", "roles").isEqualTo(expected);
    }

    public static void assertUserMatch(Iterable<User> actual, User... expected) {
        assertUserMatch(actual, Arrays.asList(expected));
    }

    public static void assertUserMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields("registered", "roles").isEqualTo(expected);
    }

    public static void assertMealMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMealMatch(Iterable<Meal> actual, Meal... expected) {
        assertMealMatch(actual, Arrays.asList(expected));
    }

    public static void assertMealMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields("registered", "roles").isEqualTo(expected);
    }
}
