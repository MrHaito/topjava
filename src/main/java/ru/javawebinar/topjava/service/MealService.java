package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, int userId) {
        return ValidationUtil.Meal.checkNotFoundMealWidthId(repository.get(id, userId), id, userId);
    }

    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public List<Meal> getByDates(int userId, LocalDate startDate, LocalDate endDate) {
        return repository.getByDates(userId, startDate, endDate);
    }

    public void update(Meal meal, int userId) {
        ValidationUtil.Meal.checkNotFoundMealWidthId(repository.save(meal, userId), meal.getId(), userId);
    }
}