package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryStorage implements Storage {
    public final Map<Integer, Meal> meals = new ConcurrentHashMap<>();

    @Override
    public Meal createOrUpdate(Meal meal) {
        return meals.put(meal.getMealId(), meal);
    }

    @Override
    public void delete(String id) {
        meals.remove(Integer.parseInt(id));
    }

    @Override
    public Meal get(String id) {
        return meals.get(Integer.parseInt(id));
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public boolean isContainsIndex(String id) {
        return meals.containsKey(Integer.parseInt(id));
    }
}
