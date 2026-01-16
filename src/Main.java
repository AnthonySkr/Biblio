import services.BookService;
import services.UserService;
import services.LoanService;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        boolean running = true;

        while (running) {
            System.out.println("\n===== BIBLIOTHÈQUE =====");
            System.out.println("1 - Livres");
            System.out.println("2 - Utilisateurs");
            System.out.println("3 - Emprunts");
            System.out.println("0 - Quitter");
            System.out.print("Choix : ");

            switch (scanner.nextLine()) {
                case "1" -> menuBooks();
                case "2" -> menuUsers();
                case "3" -> menuLoans();
                case "0" -> running = false;
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private static void menuBooks() {
        System.out.println("\na - Ajouter | l - Lister | b - Retour");
        switch (scanner.nextLine()) {
            case "a" -> {
                System.out.print("Titre : ");
                String title = scanner.nextLine();
                System.out.print("Auteur : ");
                String author = scanner.nextLine();
                System.out.print("Genre : ");
                String genre = scanner.nextLine();
                BookService.addBook(title, author, genre);
            }
            case "l" -> BookService.listBooks();
        }
    }

    private static void menuUsers() {
        System.out.println("\na - Ajouter | l - Lister | b - Retour");
        switch (scanner.nextLine()) {
            case "a" -> {
                System.out.print("Nom : ");
                UserService.addUser(scanner.nextLine());
            }
            case "l" -> UserService.listUsers();
        }
    }

    private static void menuLoans() {
        System.out.println("\ne - Emprunter | r - Retourner | l - Lister");
        switch (scanner.nextLine()) {
            case "e" -> {
                System.out.print("ID Livre : ");
                int bookId = Integer.parseInt(scanner.nextLine());
                System.out.print("ID Utilisateur : ");
                int userId = Integer.parseInt(scanner.nextLine());
                LoanService.borrowBook(bookId, userId);
            }
            case "r" -> {
                System.out.print("ID Livre : ");
                LoanService.returnBook(Integer.parseInt(scanner.nextLine()));
            }
            case "l" -> LoanService.listLoans();
        }
    }
}
