package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Storage {
    Meal createOrUpdate(Meal meal);

    void delete(Integer id);

    Meal get(Integer id);

    List<Meal> getAll();

    int size();
}
