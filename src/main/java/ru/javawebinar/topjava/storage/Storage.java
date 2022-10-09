package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Storage {
    Meal create(Meal meal);

    Meal update(String id, Meal meal);

    void delete(String id);

    Meal get(String id);

    List<Meal> getAll();

    boolean isContainsIndex(String id);
}
