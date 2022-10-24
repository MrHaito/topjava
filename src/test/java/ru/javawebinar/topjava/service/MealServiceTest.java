package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({"classpath:spring/spring-jdbc-app.xml",
        "classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(meal_1.getId(), USER_ID);
        assertMealsMatch(meal, meal_1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getWrongUserId() {
        assertThrows(NotFoundException.class, () -> service.get(meal_1.getId(), ADMIN_ID));
    }

    @Test
    public void delete() {
        service.delete(meal_1.getId(), USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(meal_1.getId(), USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void deleteWrongUserId() {
        assertThrows(NotFoundException.class, () -> service.delete(meal_1.getId(), ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> all = service.getBetweenInclusive(LocalDate.of(2020, 1, 30), LocalDate.of(2020, 1, 30), USER_ID);
        assertMealsMatch(all, meal_3, meal_2, meal_1);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMealsMatch(all, meal_7, meal_6, meal_5, meal_4, meal_3, meal_2, meal_1);
    }

    @Test
    public void update() {
        Meal updated = getMealUpdated();
        service.update(updated, USER_ID);
        assertMealsMatch(service.get(meal_1.getId(), USER_ID), getMealUpdated());
    }

    @Test
    public void updateWrongUserId() {
        Meal created = service.create(getNewMeal(), USER_ID);
        created.setCalories(2002);
        assertThrows(NotFoundException.class, () -> service.update(created, ADMIN_ID));
    }

    @Test
    public void create() {
        int userId = USER_ID;
        Meal created = service.create(getNewMeal(), userId);
        Integer newId = created.getId();
        Meal newMeal = getNewMeal();
        newMeal.setId(newId);
        assertMealsMatch(created, newMeal);
        assertMealsMatch(service.get(newId, userId), newMeal);
    }

    @Test
    public void duplicateMealCreate() {
        assertThrows(DataAccessException.class, () -> service.create(new Meal(null, meal_1.getDateTime(), "Dublicate"
                , 2000), USER_ID));
    }
}