package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.ListStorage;
import ru.javawebinar.topjava.storage.Storage;

import ru.javawebinar.topjava.util.Strategy.ConvertToMealTo;
import ru.javawebinar.topjava.util.Strategy.MealsUtilStrategy;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

public class MealServlet extends HttpServlet {
    private final Storage storage = new ListStorage();
    private final static int CALORIES = 2000;
    private final MealsUtilStrategy strategy = new ConvertToMealTo();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        storage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        storage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        storage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        storage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        storage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        storage.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        String action = req.getParameter("action");
        if (action == null) {
            List<MealTo> mealsTo = strategy.convertMealsToMealsTo(storage.getAll(), null, null, CALORIES);
            req.setAttribute("meals", mealsTo);
            req.getRequestDispatcher("/meals.jsp").forward(req, resp);
            return;
        }
        Meal meal;
        switch (action) {
            case "add":
                meal = new Meal();
                break;
            case "edit":
                meal = storage.get(uuid);
                break;
            case "delete":
                storage.delete(uuid);
                resp.sendRedirect("meals");
                return;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        req.setAttribute("meal", meal);
        req.getRequestDispatcher("/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String uuid = req.getParameter("uuid");
        LocalDateTime dateTime = TimeUtil.convertStringToLDT(req.getParameter("datetime"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));

        boolean newMeal = (uuid == null || uuid.length() == 0);
        Meal meal;
        if (!newMeal) {
            meal = storage.get(uuid);
            meal.setDateTime(dateTime);
            meal.setDescription(description);
            meal.setCalories(calories);
            storage.update(meal);
        } else {
            meal = new Meal(dateTime, description, calories);
            storage.save(meal);
        }
        resp.sendRedirect("meals");
    }
}
