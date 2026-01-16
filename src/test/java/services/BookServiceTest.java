package services;

import database.DatabaseManager;
import models.Book;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class BookServiceTest {

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
    void addBook_shouldInsertBookInDatabase() {
        int id = BookService.addBook("1984", "Orwell", "Roman");

        Book book = BookService.findById(id);
        assertNotNull(book);
        assertEquals("1984", book.getTitle());
        assertTrue(book.isAvailable());
    }

    @Test
    void updateBook_shouldModifyBookData() {
        int id = BookService.addBook("Old", "Author", "Genre");
        BookService.updateBook(id, "New", "NewAuthor", "NewGenre");

        Book book = BookService.findById(id);
        assertEquals("New", book.getTitle());
        assertEquals("NewAuthor", book.getAuthor());
    }

    @Test
    void deleteBook_shouldRemoveBook() {
        int id = BookService.addBook("Temp", "A", "G");
        BookService.deleteBook(id);

        Book book = BookService.findById(id);
        assertNull(book);
    }
}
