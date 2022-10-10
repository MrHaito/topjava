package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.MealsMemoryStorage;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private Storage storage;
    private static final int CALORIES = 2000;
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new MealsMemoryStorage();
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
        switch (action) {
            case "add":
                log.info("add meal");
                Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 0);
                req.setAttribute("meal", meal);
                break;
            case "edit":
                log.info("edit meal");
                String id = req.getParameter("id");
                meal = storage.get(Integer.parseInt(id));
                req.setAttribute("meal", meal);
                break;
            case "delete":
                log.info("delete meal");
                id = req.getParameter("id");
                storage.delete(Integer.parseInt(id));
                resp.sendRedirect("meals");
                return;
            default:
                resp.sendRedirect("meals");
                return;
        }
        req.getRequestDispatcher("/editMeal.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("getting info from edit form");
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("datetime"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));

        boolean isNewMeal = id == null || id.length() == 0;
        Meal meal = new Meal(dateTime, description, calories);
        if (!isNewMeal) {
            meal.setId(Integer.parseInt(id));
        }
        storage.createOrUpdate(meal);
        resp.sendRedirect("meals");
    }
}
