package services;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoanServiceTest {

    @Test
    void borrowBook_shouldMarkBookUnavailable() {
        BookService.addBook("Dune", "Herbert", "SF");
        UserService.addUser("Bob");

        LoanService.borrowBook(1, 1);

        assertFalse(BookService.findById(1).isAvailable());
    }

    @Test
    void borrowSameBookTwice_shouldFail() {
        BookService.addBook("It", "King", "Horreur");
        UserService.addUser("Paul");

        LoanService.borrowBook(2, 2);
        LoanService.borrowBook(2, 2);

        assertFalse(BookService.findById(2).isAvailable());
    }

    @Test
    void returnBook_shouldMakeBookAvailableAgain() {
        BookService.addBook("Foundation", "Asimov", "SF");
        UserService.addUser("Clara");

        LoanService.borrowBook(3, 3);
        LoanService.returnBook(3);

        assertTrue(BookService.findById(3).isAvailable());
    }
}