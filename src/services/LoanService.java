package services;

import models.Book;
import models.Loan;
import java.util.ArrayList;
import java.util.List;

public class LoanService {

    private static final List<Loan> loans = new ArrayList<>();
    private static int nextId = 1;

    public static void borrowBook(int bookId, int userId) {
        Book book = BookService.findById(bookId);

        if (book == null) {
            System.out.println("Livre introuvable.");
            return;
        }

        if (!book.isAvailable()) {
            System.out.println("Livre déjà emprunté.");
            return;
        }

        book.setAvailable(false);
        Loan loan = new Loan(bookId, userId);
        loan.setId(nextId++);
        loans.add(loan);
        System.out.println("Livre emprunté.");
    }

    public static void returnBook(int bookId) {
        for (Loan loan : loans) {
            if (loan.getBookId() == bookId && !loan.isReturned()) {
                loan.returnBook();
                Book book = BookService.findById(bookId);
                if (book != null) {
                    book.setAvailable(true);
                }
                System.out.println("Livre retourné.");
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

        for (Loan loan : loans) {
            System.out.println(
                    "Livre ID: " + loan.getBookId() +
                            " | Utilisateur ID: " + loan.getUserId() +
                            " | Emprunt: " + loan.getLoanDate() +
                            " | Retour: " + (loan.isReturned() ? loan.getReturnDate() : "Non rendu")
            );
        }
    }

    /**
     * Affiche l'historique des emprunts d'un utilisateur
     */
    public static void listLoansByUser(int userId) {
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

        System.out.println("\n=== Historique des emprunts de l'utilisateur #" + userId + " ===");
        for (Loan loan : userLoans) {
            System.out.println(
                    "Livre ID: " + loan.getBookId() +
                            " | Emprunt: " + loan.getLoanDate() +
                            " | Retour: " + (loan.isReturned() ? loan.getReturnDate() : "Non rendu")
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
            System.out.println(
                    "Livre ID: " + loan.getBookId() +
                            " | Utilisateur ID: " + loan.getUserId() +
                            " | Emprunté le: " + loan.getLoanDate()
            );
        }
    }

    /**
     * Obtient tous les emprunts
     */
    public static List<Loan> getAllLoans() {
        return new ArrayList<>(loans);
    }

    /**
     * Obtient un emprunt par son ID
     */
    public static Loan findById(int id) {
        for (Loan loan : loans) {
            if (loan.getId() == id) {
                return loan;
            }
        }
        return null;
    }
}
