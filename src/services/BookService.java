package services;

import database.DatabaseManager;
import models.Book;
import java.sql.*;

public class BookService {

    private static final DatabaseManager db = DatabaseManager.getInstance();

    public static void addBook(String title, String author, String genre) {
        String sql = "INSERT INTO books (title, author, genre, is_available) VALUES (?, ?, ?, 1)";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setString(3, genre);
            pstmt.executeUpdate();
            System.out.println("Livre ajouté.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du livre : " + e.getMessage());
        }
    }

    public static void listBooks() {
        String sql = "SELECT * FROM books";
        try (Statement stmt = db.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            boolean hasBooks = false;
            while (rs.next()) {
                hasBooks = true;
                Book book = new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("genre"),
                    rs.getInt("is_available") == 1
                );
                System.out.println(book);
            }

            if (!hasBooks) {
                System.out.println("Aucun livre.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des livres : " + e.getMessage());
        }
    }

    public static Book findById(int id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("genre"),
                    rs.getInt("is_available") == 1
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche du livre : " + e.getMessage());
        }
        return null;
    }

    public static void updateBook(int id, String title, String author, String genre) {
        if (findById(id) == null) {
            System.out.println("Livre introuvable.");
            return;
        }

        String sql = "UPDATE books SET title = ?, author = ?, genre = ? WHERE id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setString(3, genre);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
            System.out.println("Livre modifié.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification du livre : " + e.getMessage());
        }
    }

    public static void deleteBook(int id) {
        Book book = findById(id);
        if (book == null) {
            System.out.println("Livre introuvable.");
            return;
        }
        if (!book.isAvailable()) {
            System.out.println("Impossible de supprimer : le livre est actuellement emprunté.");
            return;
        }

        String sql = "DELETE FROM books WHERE id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Livre supprimé.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du livre : " + e.getMessage());
        }
    }

    public static void listAvailableBooks() {
        String sql = "SELECT * FROM books WHERE is_available = 1";
        try (Statement stmt = db.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            boolean hasBooks = false;
            System.out.println("=== Livres disponibles ===");
            while (rs.next()) {
                hasBooks = true;
                Book book = new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("genre"),
                    true
                );
                System.out.println(book);
            }

            if (!hasBooks) {
                System.out.println("Aucun livre disponible.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des livres : " + e.getMessage());
        }
    }

    public static void listBorrowedBooks() {
        String sql = "SELECT * FROM books WHERE is_available = 0";
        try (Statement stmt = db.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            boolean hasBooks = false;
            System.out.println("=== Livres empruntés ===");
            while (rs.next()) {
                hasBooks = true;
                Book book = new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("genre"),
                    false
                );
                System.out.println(book);
            }

            if (!hasBooks) {
                System.out.println("Aucun livre emprunté.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des livres : " + e.getMessage());
        }
    }

    public static void searchByTitle(String title) {
        String sql = "SELECT * FROM books WHERE title LIKE ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, "%" + title + "%");
            ResultSet rs = pstmt.executeQuery();

            boolean hasResults = false;
            System.out.println("=== Résultats de recherche (Titre) ===");
            while (rs.next()) {
                hasResults = true;
                Book book = new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("genre"),
                    rs.getInt("is_available") == 1
                );
                System.out.println(book);
            }

            if (!hasResults) {
                System.out.println("Aucun livre trouvé avec le titre : " + title);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche : " + e.getMessage());
        }
    }

    public static void searchByAuthor(String author) {
        String sql = "SELECT * FROM books WHERE author LIKE ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, "%" + author + "%");
            ResultSet rs = pstmt.executeQuery();

            boolean hasResults = false;
            System.out.println("=== Résultats de recherche (Auteur) ===");
            while (rs.next()) {
                hasResults = true;
                Book book = new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("genre"),
                    rs.getInt("is_available") == 1
                );
                System.out.println(book);
            }

            if (!hasResults) {
                System.out.println("Aucun livre trouvé pour l'auteur : " + author);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche : " + e.getMessage());
        }
    }

    public static void searchByGenre(String genre) {
        String sql = "SELECT * FROM books WHERE genre LIKE ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, "%" + genre + "%");
            ResultSet rs = pstmt.executeQuery();

            boolean hasResults = false;
            System.out.println("=== Résultats de recherche (Genre) ===");
            while (rs.next()) {
                hasResults = true;
                Book book = new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("genre"),
                    rs.getInt("is_available") == 1
                );
                System.out.println(book);
            }

            if (!hasResults) {
                System.out.println("Aucun livre trouvé pour le genre : " + genre);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche : " + e.getMessage());
        }
    }
}
