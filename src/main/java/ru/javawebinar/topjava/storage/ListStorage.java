package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListStorage implements Storage {
    public final List<Meal> MEALS = new CopyOnWriteArrayList<>();

    @Override
    public void save(Meal meal) {
        MEALS.add(meal);
    }

    @Override
    public void update(Meal meal) {
        MEALS.set(findIndex(meal.getUuid()), meal);
    }

    @Override
    public void delete(String uuid) {
        MEALS.remove((int) findIndex(uuid));
    }

    @Override
    public Meal get(String uuid) {
        return MEALS.get(findIndex(uuid));
    }

    @Override
    public List<Meal> getAll() {
        Comparator<Meal> COMPARATOR = Comparator.comparing(Meal::getDate).thenComparing(Meal::getDateTime);
        MEALS.sort(COMPARATOR);
        return new ArrayList<>(MEALS);
    }

    private Integer findIndex(String uuid) {
        for (Meal m : MEALS) {
            if (m.getUuid().equals(uuid)) {
                return MEALS.indexOf(m);
            }
        }
        return -1;
    }
}
