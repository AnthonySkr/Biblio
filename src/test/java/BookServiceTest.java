package services;

import org.junit.jupiter.api.Test;
import models.Book;

import static org.junit.jupiter.api.Assertions.*;

public class BookServiceTest {

    @Test
    void addBook_shouldCreateBook() {
        BookService.addBook("1984", "Orwell", "Roman");
        Book book = BookService.findById(1);

        assertNotNull(book);
        assertEquals("1984", book.getTitle());
    }
}
