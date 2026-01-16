package services;

import models.Book;
import models.Loan;
import models.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanService {

    private static final List<Loan> loans = new ArrayList<>();
    private static int nextId = 1;

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

        book.setAvailable(false);
        Loan loan = new Loan(nextId++, bookId, userId, LocalDate.now(), null);
        loans.add(loan);
        System.out.println("Livre emprunté : \"" + book.getTitle() + "\" par " + user.getName() + ".");
    }

    public static void returnBook(int bookId) {
        for (Loan loan : loans) {
            if (loan.getBookId() == bookId && !loan.isReturned()) {
                loan.returnBook();
                Book book = BookService.findById(bookId);
                if (book != null) {
                    book.setAvailable(true);
                    System.out.println("Livre retourné : \"" + book.getTitle() + "\".");
                } else {
                    System.out.println("Livre retourné.");
                }
                return;
            }
        }
        System.out.println("Aucun emprunt actif pour ce livre.");
    }

    public static void listLoans() {
        if (loans.isEmpty()) {
            System.out.println("Aucun emprunt.");
            return;
        }

        System.out.println("\n=== Tous les emprunts ===");
        for (Loan loan : loans) {
            String bookTitle = getBookTitle(loan.getBookId());
            String userName = getUserName(loan.getUserId());

            System.out.println(
                    "Livre: \"" + bookTitle + "\" | " +
                    "Utilisateur: " + userName + " | " +
                    "Emprunt: " + loan.getLoanDate() + " | " +
                    "Retour: " + (loan.isReturned() ? loan.getReturnDate() : "Non rendu")
            );
        }
    }

    /**
     * Affiche l'historique des emprunts d'un utilisateur
     */
    public static void listLoansByUser(int userId) {
        User user = UserService.findById(userId);
        if (user == null) {
            System.out.println("Utilisateur introuvable.");
            return;
        }

        List<Loan> userLoans = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.getUserId() == userId) {
                userLoans.add(loan);
            }
        }

        if (userLoans.isEmpty()) {
            System.out.println("Aucun emprunt pour cet utilisateur.");
            return;
        }

        System.out.println("\n=== Historique des emprunts de " + user.getName() + " ===");
        for (Loan loan : userLoans) {
            String bookTitle = getBookTitle(loan.getBookId());

            System.out.println(
                    "Livre: \"" + bookTitle + "\" | " +
                    "Emprunt: " + loan.getLoanDate() + " | " +
                    "Retour: " + (loan.isReturned() ? loan.getReturnDate() : "Non rendu")
            );
        }
    }

    /**
     * Liste uniquement les emprunts actifs
     */
    public static void listActiveLoans() {
        List<Loan> activeLoans = new ArrayList<>();
        for (Loan loan : loans) {
            if (!loan.isReturned()) {
                activeLoans.add(loan);
            }
        }

        if (activeLoans.isEmpty()) {
            System.out.println("Aucun emprunt actif.");
            return;
        }

        System.out.println("\n=== Emprunts actifs ===");
        for (Loan loan : activeLoans) {
            String bookTitle = getBookTitle(loan.getBookId());
            String userName = getUserName(loan.getUserId());

            System.out.println(
                    "Livre: \"" + bookTitle + "\" | " +
                    "Utilisateur: " + userName + " | " +
                    "Emprunté le: " + loan.getLoanDate()
            );
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
