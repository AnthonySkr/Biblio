package services;

import database.DatabaseManager;
import models.Book;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class LoanServiceTest {

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

        int bookId = BookService.addBook("Dune", "Herbert", "SF");
        int userId = UserService.addUser("Paul");

        // Stocker les IDs pour les tests
        this.bookId = bookId;
        this.userId = userId;
    }

    private int bookId;
    private int userId;

    @Test
    void borrowBook_shouldMarkBookAsUnavailable() {
        LoanService.borrowBook(bookId, userId);

        Book book = BookService.findById(bookId);
        assertNotNull(book);
        assertFalse(book.isAvailable());
    }

    @Test
    void borrowBook_twice_shouldKeepBookUnavailable() {
        LoanService.borrowBook(bookId, userId);
        LoanService.borrowBook(bookId, userId);

        Book book = BookService.findById(bookId);
        assertFalse(book.isAvailable());
    }

    @Test
    void returnBook_shouldMakeBookAvailableAgain() {
        LoanService.borrowBook(bookId, userId);
        LoanService.returnBook(bookId);

        Book book = BookService.findById(bookId);
        assertTrue(book.isAvailable());
    }
}
