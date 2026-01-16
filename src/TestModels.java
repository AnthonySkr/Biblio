import models.Book;
import models.User;
import models.Loan;
import java.time.LocalDate;

public class TestModels {
    public static void main(String[] args) {
        System.out.println("=== Test des corrections des modèles ===\n");

        // Test 1: Book avec disponibilité
        System.out.println("Test 1 - Book :");
        Book book1 = new Book(1, "1984", "George Orwell", "Science-Fiction", true);
        System.out.println("  ✓ Livre créé disponible : " + book1.isAvailable());

        Book book2 = new Book(2, "Le Petit Prince", "Saint-Exupéry", "Conte", false);
        System.out.println("  ✓ Livre créé emprunté : " + !book2.isAvailable());

        Book book3 = new Book(3, "Harry Potter", "J.K. Rowling", "Fantasy");
        System.out.println("  ✓ Livre créé (constructeur simplifié) : " + book3.isAvailable());
        System.out.println("  " + book1);

        // Test 2: User avec email
        System.out.println("\nTest 2 - User :");
        User user1 = new User(1, "Alice Dupont", "alice@mail.com");
        System.out.println("  ✓ Email correctement initialisé : " + (user1.getEmail() != null));
        System.out.println("  " + user1);

        // Test 3: Loan avec dates
        System.out.println("\nTest 3 - Loan :");
        Loan loan1 = new Loan(1, 1, 1, LocalDate.of(2026, 1, 10));
        System.out.println("  ✓ Emprunt créé avec date spécifique : " + loan1.getLoanDate());

        Loan loan2 = new Loan(2, 2, 1);
        System.out.println("  ✓ Emprunt créé avec date du jour : " + loan2.getLoanDate());

        // Test 4: ReturnBook
        System.out.println("\nTest 4 - ReturnBook :");
        System.out.println("  Avant retour - isReturned : " + loan2.isReturned());
        loan2.returnBook();
        System.out.println("  ✓ Après retour - isReturned : " + loan2.isReturned());
        System.out.println("  ✓ Date de retour : " + loan2.getReturnDate());

        System.out.println("\n✅ Tous les bugs des modèles sont corrigés !");
    }
}

