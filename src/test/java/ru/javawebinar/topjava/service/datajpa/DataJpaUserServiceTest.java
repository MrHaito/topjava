package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

import java.util.ArrayList;

import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Test
    public void getWithMeals() {
        User user = service.getWithMeals(USER_ID);
        USER_MATCHER_WITH_MEALS.assertMatch(user, service.get(USER_ID));
    }

    @Test
    public void getWithEmptyMeals() {
        User user = new User(UserTestData.user);
        user.setMeals(new ArrayList<>());
        User oldUser = UserTestData.user;
        oldUser.setMeals(new ArrayList<>());
        USER_MATCHER_WITH_MEALS.assertMatch(user, oldUser);
    }
}
