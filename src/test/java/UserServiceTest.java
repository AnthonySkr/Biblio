package services;

import org.junit.jupiter.api.Test;
import models.User;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Test
    void addUser_shouldCreateUser() {
        UserService.addUser("Alice");
        User user = UserService.findById(1);

        assertNotNull(user);
        assertEquals("Alice", user.getName());
    }
}
