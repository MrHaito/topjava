package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

public class Meal {
    private String uuid;

    private LocalDateTime dateTime;

    private String description;

    private int calories;

    public Meal() {
        this.dateTime = LocalDateTime.now();
        this.description = "";
        this.calories = 0;
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this.uuid = UUID.randomUUID().toString();
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public String getUuid() {
        return uuid;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return getCalories() == meal.getCalories() && Objects.equals(getUuid(), meal.getUuid()) && Objects.equals(getDateTime(), meal.getDateTime()) && Objects.equals(getDescription(), meal.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getDateTime(), getDescription(), getCalories());
    }
}
