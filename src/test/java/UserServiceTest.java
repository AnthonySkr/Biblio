import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import models.User;
import services.UserService;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @BeforeEach
    void setUp() {
        UserService.clearAll();
    }

    @Test
    void addUser_shouldCreateUser() {
        UserService.addUser("Alice");
        User user = UserService.findById(1);

        assertNotNull(user);
        assertEquals("Alice", user.getName());
    }
}
