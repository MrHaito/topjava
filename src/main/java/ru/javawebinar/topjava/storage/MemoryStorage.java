package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryStorage implements Storage {
    private static final AtomicInteger counter = new AtomicInteger(1);
    public final Map<Integer, Meal> meals = new ConcurrentHashMap<>();

    @Override
    public Meal create(Meal meal) {
        meal.setMealId(counter.getAndIncrement());
        return meals.put(meal.getMealId(), meal);
    }

    @Override
    public Meal update(String id, Meal meal) {
        return meals.put(Integer.parseInt(id), meal);
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
        if (id.equals("")) {
            return false;
        }
        return meals.containsKey(Integer.parseInt(id));
    }
}
