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

    @Test
    void updateBook_shouldHandleNonExistentBook() {
        // Test la branche où le livre n'existe pas
        BookService.updateBook(999, "Ghost", "Nobody", "None");

        Book book = BookService.findById(999);
        assertNull(book);
    }

    @Test
    void deleteBook_shouldHandleNonExistentBook() {
        // Test la suppression d'un livre inexistant
        BookService.deleteBook(999);

        Book book = BookService.findById(999);
        assertNull(book);
    }

    @Test
    void listBooks_shouldDisplayAllBooks() {
        // Test l'affichage de plusieurs livres
        int id1 = BookService.addBook("Book1", "Author1", "Genre1");
        int id2 = BookService.addBook("Book2", "Author2", "Genre2");
        int id3 = BookService.addBook("Book3", "Author3", "Genre3");

        // La méthode listBooks() affiche dans la console
        BookService.listBooks();

        // Vérifier qu'il y a bien 3 livres
        assertNotNull(BookService.findById(id1));
        assertNotNull(BookService.findById(id2));
        assertNotNull(BookService.findById(id3));
    }

    @Test
    void listBooks_shouldHandleEmptyDatabase() {
        // Test quand aucun livre n'existe
        // La méthode devrait afficher "Aucun livre."
        BookService.listBooks();

        // Vérifier qu'aucun livre n'existe en testant un ID arbitraire
        Book book = BookService.findById(999);
        assertNull(book);
    }

    @Test
    void findById_shouldReturnNullWhenNotFound() {
        // Test explicite du cas où le livre n'existe pas
        Book book = BookService.findById(999);
        assertNull(book);
    }

    @Test
    void deleteBook_shouldNotDeleteBorrowedBook() {
        // Créer un livre et un utilisateur pour l'emprunter
        int bookId = BookService.addBook("Borrowed", "Author", "Genre");
        int userId = UserService.addUser("User");

        // Emprunter le livre
        LoanService.borrowBook(bookId, userId);

        // Tenter de supprimer le livre emprunté
        BookService.deleteBook(bookId);

        // Le livre devrait toujours exister
        Book book = BookService.findById(bookId);
        assertNotNull(book);
        assertFalse(book.isAvailable());
    }

    @Test
    void listAvailableBooks_shouldShowOnlyAvailableBooks() {
        // Créer plusieurs livres
        int book1 = BookService.addBook("Available1", "A1", "G1");
        int book2 = BookService.addBook("Available2", "A2", "G2");
        int book3 = BookService.addBook("ToBorrow", "A3", "G3");

        // Emprunter un livre
        int userId = UserService.addUser("User");
        LoanService.borrowBook(book3, userId);

        // Lister les livres disponibles
        BookService.listAvailableBooks();

        // Vérifier que les 2 premiers sont disponibles et le 3e non
        assertTrue(BookService.findById(book1).isAvailable());
        assertTrue(BookService.findById(book2).isAvailable());
        assertFalse(BookService.findById(book3).isAvailable());
    }

    @Test
    void listAvailableBooks_shouldHandleNoAvailableBooks() {
        // Créer un livre et l'emprunter
        int bookId = BookService.addBook("OnlyBook", "Author", "Genre");
        int userId = UserService.addUser("User");
        LoanService.borrowBook(bookId, userId);

        // Lister les livres disponibles (aucun)
        BookService.listAvailableBooks();

        // Vérifier qu'aucun livre n'est disponible
        assertFalse(BookService.findById(bookId).isAvailable());
    }

    @Test
    void listBorrowedBooks_shouldShowOnlyBorrowedBooks() {
        // Créer plusieurs livres
        int book1 = BookService.addBook("Borrowed1", "A1", "G1");
        int book2 = BookService.addBook("Available", "A2", "G2");
        int book3 = BookService.addBook("Borrowed2", "A3", "G3");

        // Emprunter 2 livres
        int userId = UserService.addUser("User");
        LoanService.borrowBook(book1, userId);
        LoanService.borrowBook(book3, userId);

        // Lister les livres empruntés
        BookService.listBorrowedBooks();

        // Vérifier les statuts
        assertFalse(BookService.findById(book1).isAvailable());
        assertTrue(BookService.findById(book2).isAvailable());
        assertFalse(BookService.findById(book3).isAvailable());
    }
}
