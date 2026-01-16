package models;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ModelsTest {

    @Test
    void book_shouldCreateAndGetFields() {
        Book book = new Book(1, "Title", "Author", "Genre", true);

        assertEquals(1, book.getId());
        assertEquals("Title", book.getTitle());
        assertEquals("Author", book.getAuthor());
        assertEquals("Genre", book.getGenre());
        assertTrue(book.isAvailable());
    }

    @Test
    void book_shouldSetAvailability() {
        Book book = new Book(1, "Title", "Author", "Genre", true);

        book.setAvailable(false);
        assertFalse(book.isAvailable());

        book.setAvailable(true);
        assertTrue(book.isAvailable());
    }

    @Test
    void book_toStringShouldFormatCorrectly() {
        Book available = new Book(1, "1984", "Orwell", "Dystopie", true);
        Book unavailable = new Book(2, "Dune", "Herbert", "SF", false);

        String availableStr = available.toString();
        String unavailableStr = unavailable.toString();

        assertTrue(availableStr.contains("1984"));
        assertTrue(availableStr.contains("Orwell"));
        assertTrue(availableStr.contains("Disponible"));

        assertTrue(unavailableStr.contains("Dune"));
        assertTrue(unavailableStr.contains("Emprunté"));
    }

    @Test
    void user_shouldCreateAndGetFields() {
        User user = new User(1, "Alice");

        assertEquals(1, user.getId());
        assertEquals("Alice", user.getName());
    }

    @Test
    void user_toStringShouldFormatCorrectly() {
        User user = new User(5, "Bob");

        String str = user.toString();

        assertTrue(str.contains("5"));
        assertTrue(str.contains("Bob"));
    }

    @Test
    void loan_shouldCreateAndGetFields() {
        LocalDate loanDate = LocalDate.of(2024, 1, 15);
        Loan loan = new Loan(1, 10, 20, loanDate, null);

        assertEquals(1, loan.getId());
        assertEquals(10, loan.getBookId());
        assertEquals(20, loan.getUserId());
        assertEquals(loanDate, loan.getLoanDate());
        assertNull(loan.getReturnDate());
        assertFalse(loan.isReturned());
    }

    @Test
    void loan_shouldSetReturnDate() {
        LocalDate loanDate = LocalDate.of(2024, 1, 15);
        LocalDate returnDate = LocalDate.of(2024, 2, 15);
        Loan loan = new Loan(1, 10, 20, loanDate, null);

        assertNull(loan.getReturnDate());
        assertFalse(loan.isReturned());

        loan.setReturnDate(returnDate);
        assertEquals(returnDate, loan.getReturnDate());
        assertTrue(loan.isReturned());
    }

    @Test
    void loan_toStringShouldFormatCorrectly() {
        LocalDate loanDate = LocalDate.of(2024, 1, 1);
        LocalDate returnDate = LocalDate.of(2024, 1, 15);

        Loan activeLoan = new Loan(1, 5, 3, loanDate, null);
        Loan returnedLoan = new Loan(2, 6, 4, loanDate, returnDate);

        String activeStr = activeLoan.toString();
        String returnedStr = returnedLoan.toString();

        assertTrue(activeStr.contains("2024-01-01"));
        assertTrue(activeStr.contains("En cours"));

        assertTrue(returnedStr.contains("2024-01-15"));
        assertTrue(returnedStr.contains("Retourné"));
    }

    @Test
    void loan_returnBookShouldSetReturnDate() {
        LocalDate loanDate = LocalDate.of(2024, 1, 1);
        Loan loan = new Loan(1, 10, 20, loanDate, null);

        assertFalse(loan.isReturned());

        loan.returnBook();

        assertTrue(loan.isReturned());
        assertNotNull(loan.getReturnDate());
    }
}
