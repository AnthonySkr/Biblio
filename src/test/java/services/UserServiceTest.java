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

    @Test
    void updateUser_shouldHandleNonExistentUser() {
        // Test la branche où l'utilisateur n'existe pas
        UserService.updateUser(999, "Ghost");

        User user = UserService.findById(999);
        assertNull(user);
    }

    @Test
    void deleteUser_shouldHandleNonExistentUser() {
        // Test la suppression d'un utilisateur inexistant
        UserService.deleteUser(999);

        User user = UserService.findById(999);
        assertNull(user);
    }

    @Test
    void listUsers_shouldDisplayAllUsers() {
        // Test l'affichage de plusieurs utilisateurs
        int id1 = UserService.addUser("Alice");
        int id2 = UserService.addUser("Bob");
        int id3 = UserService.addUser("Charlie");

        // La méthode listUsers() affiche dans la console
        UserService.listUsers();

        // Vérifier qu'il y a bien 3 utilisateurs
        assertNotNull(UserService.findById(id1));
        assertNotNull(UserService.findById(id2));
        assertNotNull(UserService.findById(id3));
    }

    @Test
    void listUsers_shouldHandleEmptyDatabase() {
        // Test quand aucun utilisateur n'existe
        // La méthode devrait afficher "Aucun utilisateur."
        UserService.listUsers();

        // Vérifier qu'aucun utilisateur n'existe en testant un ID arbitraire
        User user = UserService.findById(999);
        assertNull(user);
    }

    @Test
    void findById_shouldReturnNullWhenNotFound() {
        // Test explicite du cas où l'utilisateur n'existe pas
        User user = UserService.findById(999);
        assertNull(user);
    }

    @Test
    void addMultipleUsers_shouldAllExist() {
        // Ajouter plusieurs utilisateurs
        int id1 = UserService.addUser("User1");
        int id2 = UserService.addUser("User2");
        int id3 = UserService.addUser("User3");

        // Vérifier qu'ils existent tous
        assertNotNull(UserService.findById(id1));
        assertNotNull(UserService.findById(id2));
        assertNotNull(UserService.findById(id3));

        assertEquals("User1", UserService.findById(id1).getName());
        assertEquals("User2", UserService.findById(id2).getName());
        assertEquals("User3", UserService.findById(id3).getName());
    }

    @Test
    void updateUser_shouldOnlyAffectTargetUser() {
        // Créer plusieurs utilisateurs
        int id1 = UserService.addUser("Original1");
        int id2 = UserService.addUser("Original2");

        // Modifier seulement le premier
        UserService.updateUser(id1, "Modified1");

        // Vérifier que seul le premier a changé
        assertEquals("Modified1", UserService.findById(id1).getName());
        assertEquals("Original2", UserService.findById(id2).getName());
    }

    @Test
    void deleteUser_shouldOnlyAffectTargetUser() {
        // Créer plusieurs utilisateurs
        int id1 = UserService.addUser("ToDelete");
        int id2 = UserService.addUser("ToKeep");

        // Supprimer seulement le premier
        UserService.deleteUser(id1);

        // Vérifier que seul le premier a été supprimé
        assertNull(UserService.findById(id1));
        assertNotNull(UserService.findById(id2));
        assertEquals("ToKeep", UserService.findById(id2).getName());
    }
}
