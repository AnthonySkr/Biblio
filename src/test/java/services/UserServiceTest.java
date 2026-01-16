package services;

import database.DatabaseManager;
import models.User;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private static Connection connection;

    @BeforeAll
    static void init() {
        connection = DatabaseManager.getInstance().getConnection();
    }

    @BeforeEach
    void cleanDatabase() throws Exception {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM loans");
            stmt.execute("DELETE FROM books");
            stmt.execute("DELETE FROM users");
        }
    }

    @Test
    void addUser_shouldInsertUser() {
        int userId = UserService.addUser("Alice"); // récupère l'ID généré

        User user = UserService.findById(userId);
        assertNotNull(user);
        assertEquals("Alice", user.getName());
    }

    @Test
    void updateUser_shouldChangeName() {
        int userId = UserService.addUser("Bob");
        UserService.updateUser(userId, "Robert");

        User user = UserService.findById(userId);
        assertEquals("Robert", user.getName());
    }

    @Test
    void deleteUser_shouldRemoveUser() {
        int userId = UserService.addUser("Charlie");
        UserService.deleteUser(userId);

        User user = UserService.findById(userId);
        assertNull(user);
    }
}
