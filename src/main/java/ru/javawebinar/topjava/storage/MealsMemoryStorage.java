package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealsMemoryStorage implements Storage {
    private final AtomicInteger counter = new AtomicInteger(1);
    private final Map<Integer, Meal> meals = new ConcurrentHashMap<>();

    public MealsMemoryStorage() {
        MealsUtil.meals.forEach(meal -> {
            Integer id = counter.getAndIncrement();
            meal.setId(id);
            meals.put(id, meal);
        });
    }

    @Override
    public Meal createOrUpdate(Meal meal) {
        if (meal.getId() == null) {
            meal.setId(counter.getAndIncrement());
        }
        meals.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(Integer id) {
        meals.remove(id);
    }

    @Override
    public Meal get(Integer id) {
        return meals.get(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public int size() {
        return meals.size();
    }
}
