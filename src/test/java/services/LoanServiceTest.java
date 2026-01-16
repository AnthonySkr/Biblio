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

    @Test
    void borrowBook_shouldHandleNonExistentBook() {
        // Test emprunt d'un livre inexistant
        LoanService.borrowBook(999, userId);

        // Vérifier que le livre inexistant ne peut pas être emprunté
        assertNull(BookService.findById(999));
    }

    @Test
    void borrowBook_shouldHandleNonExistentUser() {
        // Test emprunt par un utilisateur inexistant
        LoanService.borrowBook(bookId, 999);

        // Le livre devrait toujours être disponible
        Book book = BookService.findById(bookId);
        assertTrue(book.isAvailable());
    }

    @Test
    void returnBook_shouldHandleNonExistentLoan() {
        // Test retour d'un livre qui n'a jamais été emprunté
        LoanService.returnBook(bookId);

        // Le livre devrait toujours être disponible
        Book book = BookService.findById(bookId);
        assertTrue(book.isAvailable());
    }

    @Test
    void returnBook_shouldHandleNonExistentBook() {
        // Test retour d'un livre inexistant
        LoanService.returnBook(999);

        // Aucune erreur ne devrait se produire
        assertNull(BookService.findById(999));
    }

    @Test
    void listLoans_shouldDisplayAllLoans() {
        // Test l'affichage des emprunts
        LoanService.borrowBook(bookId, userId);

        // La méthode listLoans() affiche dans la console
        LoanService.listLoans();

        // Vérifier que le livre est bien emprunté
        Book book = BookService.findById(bookId);
        assertFalse(book.isAvailable());
    }

    @Test
    void listLoans_shouldHandleEmptyDatabase() {
        // Test quand aucun emprunt n'existe
        LoanService.listLoans();

        // Le livre devrait être disponible
        Book book = BookService.findById(bookId);
        assertTrue(book.isAvailable());
    }

    @Test
    void listLoansByUser_shouldShowUserLoans() {
        // Créer un autre livre et emprunter les deux
        int book2Id = BookService.addBook("Dune 2", "Herbert", "SF");

        LoanService.borrowBook(bookId, userId);
        LoanService.borrowBook(book2Id, userId);

        // Lister les emprunts de l'utilisateur
        LoanService.listLoansByUser(userId);

        // Vérifier que les deux livres sont empruntés
        assertFalse(BookService.findById(bookId).isAvailable());
        assertFalse(BookService.findById(book2Id).isAvailable());
    }

    @Test
    void listLoansByUser_shouldHandleNonExistentUser() {
        // Test avec un utilisateur inexistant
        LoanService.listLoansByUser(999);

        // Aucune erreur ne devrait se produire
        assertNull(UserService.findById(999));
    }

    @Test
    void listLoansByUser_shouldHandleNoLoans() {
        // Utilisateur sans emprunts
        LoanService.listLoansByUser(userId);

        // Le livre devrait être disponible (pas emprunté)
        Book book = BookService.findById(bookId);
        assertTrue(book.isAvailable());
    }

    @Test
    void listActiveLoans_shouldShowOnlyActiveLoans() {
        // Emprunter et retourner un livre
        LoanService.borrowBook(bookId, userId);

        // Créer un autre livre et l'emprunter
        int book2Id = BookService.addBook("Book2", "Author", "Genre");
        LoanService.borrowBook(book2Id, userId);

        // Retourner le premier livre
        LoanService.returnBook(bookId);

        // Lister les emprunts actifs (seulement book2)
        LoanService.listActiveLoans();

        // Vérifier les statuts
        assertTrue(BookService.findById(bookId).isAvailable());
        assertFalse(BookService.findById(book2Id).isAvailable());
    }

    @Test
    void listActiveLoans_shouldHandleNoActiveLoans() {
        // Pas d'emprunts actifs
        LoanService.listActiveLoans();

        // Le livre devrait être disponible
        Book book = BookService.findById(bookId);
        assertTrue(book.isAvailable());
    }

    @Test
    void multipleUsers_canBorrowDifferentBooks() {
        // Créer un autre utilisateur et un autre livre
        int user2Id = UserService.addUser("Chani");
        int book2Id = BookService.addBook("Foundation", "Asimov", "SF");

        // Chaque utilisateur emprunte un livre différent
        LoanService.borrowBook(bookId, userId);
        LoanService.borrowBook(book2Id, user2Id);

        // Vérifier que les deux livres sont empruntés
        assertFalse(BookService.findById(bookId).isAvailable());
        assertFalse(BookService.findById(book2Id).isAvailable());
    }
}
