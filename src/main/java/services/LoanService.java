package services;

import database.DatabaseManager;
import models.Book;
import models.User;
import java.sql.*;
import java.time.LocalDate;

public class LoanService {

    private static final DatabaseManager db = DatabaseManager.getInstance();

    public static void borrowBook(int bookId, int userId) {
        Book book = BookService.findById(bookId);
        User user = UserService.findById(userId);

        if (book == null) {
            System.out.println("Livre introuvable.");
            return;
        }

        if (user == null) {
            System.out.println("Utilisateur introuvable.");
            return;
        }

        if (!book.isAvailable()) {
            System.out.println("Livre déjà emprunté.");
            return;
        }

        // Enregistrer l'emprunt
        String sql = "INSERT INTO loans (book_id, user_id, loan_date) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            pstmt.setInt(2, userId);
            pstmt.setString(3, LocalDate.now().toString());
            pstmt.executeUpdate();

            // Mettre à jour la disponibilité du livre
            String updateBook = "UPDATE books SET is_available = 0 WHERE id = ?";
            try (PreparedStatement updateStmt = db.getConnection().prepareStatement(updateBook)) {
                updateStmt.setInt(1, bookId);
                updateStmt.executeUpdate();
            }

            System.out.println("Livre emprunté : \"" + book.getTitle() + "\" par " + user.getName() + ".");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'emprunt : " + e.getMessage());
        }
    }

    public static void returnBook(int bookId) {
        // Trouver l'emprunt actif pour ce livre
        String findLoan = "SELECT * FROM loans WHERE book_id = ? AND return_date IS NULL";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(findLoan)) {
            pstmt.setInt(1, bookId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int loanId = rs.getInt("id");

                // Mettre à jour la date de retour
                String updateLoan = "UPDATE loans SET return_date = ? WHERE id = ?";
                try (PreparedStatement updateStmt = db.getConnection().prepareStatement(updateLoan)) {
                    updateStmt.setString(1, LocalDate.now().toString());
                    updateStmt.setInt(2, loanId);
                    updateStmt.executeUpdate();
                }

                // Mettre à jour la disponibilité du livre
                String updateBook = "UPDATE books SET is_available = 1 WHERE id = ?";
                try (PreparedStatement updateStmt = db.getConnection().prepareStatement(updateBook)) {
                    updateStmt.setInt(1, bookId);
                    updateStmt.executeUpdate();
                }

                Book book = BookService.findById(bookId);
                if (book != null) {
                    System.out.println("Livre retourné : \"" + book.getTitle() + "\".");
                } else {
                    System.out.println("Livre retourné.");
                }
            } else {
                System.out.println("Aucun emprunt actif pour ce livre.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors du retour : " + e.getMessage());
        }
    }

    public static void listLoans() {
        String sql = "SELECT * FROM loans";
        try (Statement stmt = db.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            boolean hasLoans = false;
            System.out.println("\n=== Tous les emprunts ===");
            while (rs.next()) {
                hasLoans = true;
                int bookId = rs.getInt("book_id");
                int userId = rs.getInt("user_id");
                String loanDate = rs.getString("loan_date");
                String returnDate = rs.getString("return_date");

                String bookTitle = getBookTitle(bookId);
                String userName = getUserName(userId);

                System.out.println(
                        "Livre: \"" + bookTitle + "\" | " +
                                "Utilisateur: " + userName + " | " +
                                "Emprunt: " + loanDate + " | " +
                                "Retour: " + (returnDate != null ? returnDate : "Non rendu")
                );
            }

            if (!hasLoans) {
                System.out.println("Aucun emprunt.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des emprunts : " + e.getMessage());
        }
    }

    public static void listLoansByUser(int userId) {
        User user = UserService.findById(userId);
        if (user == null) {
            System.out.println("Utilisateur introuvable.");
            return;
        }

        String sql = "SELECT * FROM loans WHERE user_id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            boolean hasLoans = false;
            System.out.println("\n=== Historique des emprunts de " + user.getName() + " ===");
            while (rs.next()) {
                hasLoans = true;
                int bookId = rs.getInt("book_id");
                String loanDate = rs.getString("loan_date");
                String returnDate = rs.getString("return_date");

                String bookTitle = getBookTitle(bookId);

                System.out.println(
                        "Livre: \"" + bookTitle + "\" | " +
                                "Emprunt: " + loanDate + " | " +
                                "Retour: " + (returnDate != null ? returnDate : "Non rendu")
                );
            }

            if (!hasLoans) {
                System.out.println("Aucun emprunt pour cet utilisateur.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des emprunts : " + e.getMessage());
        }
    }

    public static void listActiveLoans() {
        String sql = "SELECT * FROM loans WHERE return_date IS NULL";
        try (Statement stmt = db.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            boolean hasLoans = false;
            System.out.println("\n=== Emprunts actifs ===");
            while (rs.next()) {
                hasLoans = true;
                int bookId = rs.getInt("book_id");
                int userId = rs.getInt("user_id");
                String loanDate = rs.getString("loan_date");

                String bookTitle = getBookTitle(bookId);
                String userName = getUserName(userId);

                System.out.println(
                        "Livre: \"" + bookTitle + "\" | " +
                                "Utilisateur: " + userName + " | " +
                                "Emprunté le: " + loanDate
                );
            }

            if (!hasLoans) {
                System.out.println("Aucun emprunt actif.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des emprunts actifs : " + e.getMessage());
        }
    }

    private static String getBookTitle(int bookId) {
        Book book = BookService.findById(bookId);
        return (book != null) ? book.getTitle() : "Livre #" + bookId;
    }

    private static String getUserName(int userId) {
        User user = UserService.findById(userId);
        return (user != null) ? user.getName() : "Utilisateur #" + userId;
    }
}
