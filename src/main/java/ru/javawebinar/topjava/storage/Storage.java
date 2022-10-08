package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Storage {
    void save(Meal meal);

    void update(Meal meal);

    void delete(String uuid);

    Meal get(String uuid);

    List<Meal> getAll();
}
