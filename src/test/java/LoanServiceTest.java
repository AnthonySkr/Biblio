import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.BookService;
import services.UserService;
import services.LoanService;

import static org.junit.jupiter.api.Assertions.*;

public class LoanServiceTest {

    @BeforeEach
    void setUp() {
        BookService.clearAll();
        UserService.clearAll();
        LoanService.clearAll();
    }

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

        LoanService.borrowBook(1, 1);
        LoanService.borrowBook(1, 1);

        assertFalse(BookService.findById(1).isAvailable());
    }

    @Test
    void returnBook_shouldMakeBookAvailableAgain() {
        BookService.addBook("Foundation", "Asimov", "SF");
        UserService.addUser("Clara");

        LoanService.borrowBook(1, 1);
        LoanService.returnBook(1);

        assertTrue(BookService.findById(1).isAvailable());
    }
}
