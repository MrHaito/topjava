package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.MemoryStorage;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static Storage storage;
    private static final int CALORIES = 2000;
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new MemoryStorage();
        MealsUtil.meals.forEach(meal -> storage.createOrUpdate(meal));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            log.info("no action");
            List<MealTo> mealsTo = MealsUtil.filteredByStreams(storage.getAll(), CALORIES);
            req.setAttribute("meals", mealsTo);
            req.getRequestDispatcher("/meals.jsp").forward(req, resp);
            return;
        }
        Meal meal;
        switch (action) {
            case "add":
                log.info("add meal");
                meal = new Meal(LocalDateTime.now(), "", 0);
                req.setAttribute("meal", meal);
                break;
            case "edit":
                log.info("edit meal");
                String id = req.getParameter("id");
                meal = storage.get(id);
                req.setAttribute("meal", meal);
                break;
            case "delete":
                log.info("delete meal");
                id = req.getParameter("id");
                storage.delete(id);
                resp.sendRedirect("meals");
                return;
            default:
                log.info("wrong action");
                resp.sendRedirect("meals");
                return;
        }
        req.getRequestDispatcher("/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("datetime"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));

        boolean isOldMeal = (storage.isContainsIndex(id));
        Meal meal;
        if (isOldMeal) {
            meal = storage.get(id);
            meal.setDateTime(dateTime);
            meal.setDescription(description);
            meal.setCalories(calories);
        } else {
            meal = new Meal(dateTime, description, calories);
        }
        storage.createOrUpdate(meal);
        log.info("success update or create meal");
        resp.sendRedirect("meals");
    }
}
