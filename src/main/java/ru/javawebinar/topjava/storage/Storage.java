package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Storage {
    Meal create(Meal meal);

    Meal update(Meal meal);

    void delete(int id);

    Meal get(int id);

    List<Meal> getAll();
}
