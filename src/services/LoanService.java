package services;

import models.Book;
import models.Loan;
import java.util.ArrayList;
import java.util.List;

public class LoanService {

    private static final List<Loan> loans = new ArrayList<>();

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
        loans.add(new Loan(bookId, userId));
        System.out.println("Livre emprunté.");
    }

    public static void returnBook(int bookId) {
        for (Loan loan : loans) {
            if (loan.getBookId() == bookId && !loan.isReturned()) {
                loan.returnBook();
                BookService.findById(bookId).setAvailable(true);
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
}
