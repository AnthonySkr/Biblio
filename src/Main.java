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
                case "0" -> {
                    running = false;
                    System.out.println("Au revoir !");
                }
                default -> System.out.println("Choix invalide.");
            }
        }
        scanner.close();
    }

    private static void menuBooks() {
        System.out.println("\n=== MENU LIVRES ===");
        System.out.println("a - Ajouter un livre");
        System.out.println("m - Modifier un livre");
        System.out.println("s - Supprimer un livre");
        System.out.println("l - Lister tous les livres");
        System.out.println("d - Lister les livres disponibles");
        System.out.println("e - Lister les livres empruntés");
        System.out.println("t - Rechercher par titre");
        System.out.println("u - Rechercher par auteur");
        System.out.println("g - Rechercher par genre");
        System.out.println("b - Retour");
        System.out.print("Choix : ");

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
            case "m" -> {
                try {
                    System.out.print("ID du livre à modifier : ");
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.print("Nouveau titre : ");
                    String title = scanner.nextLine();
                    System.out.print("Nouvel auteur : ");
                    String author = scanner.nextLine();
                    System.out.print("Nouveau genre : ");
                    String genre = scanner.nextLine();
                    BookService.updateBook(id, title, author, genre);
                } catch (NumberFormatException e) {
                    System.out.println("ID invalide. Veuillez entrer un nombre.");
                }
            }
            case "s" -> {
                try {
                    System.out.print("ID du livre à supprimer : ");
                    int id = Integer.parseInt(scanner.nextLine());
                    BookService.deleteBook(id);
                } catch (NumberFormatException e) {
                    System.out.println("ID invalide. Veuillez entrer un nombre.");
                }
            }
            case "l" -> BookService.listBooks();
            case "d" -> BookService.listAvailableBooks();
            case "e" -> BookService.listBorrowedBooks();
            case "t" -> {
                System.out.print("Rechercher par titre : ");
                String title = scanner.nextLine();
                BookService.searchByTitle(title);
            }
            case "u" -> {
                System.out.print("Rechercher par auteur : ");
                String author = scanner.nextLine();
                BookService.searchByAuthor(author);
            }
            case "g" -> {
                System.out.print("Rechercher par genre : ");
                String genre = scanner.nextLine();
                BookService.searchByGenre(genre);
            }
            case "b" -> {}
            default -> System.out.println("Choix invalide.");
        }
    }

    private static void menuUsers() {
        System.out.println("\n=== MENU UTILISATEURS ===");
        System.out.println("a - Ajouter un utilisateur");
        System.out.println("m - Modifier un utilisateur");
        System.out.println("s - Supprimer un utilisateur");
        System.out.println("l - Lister tous les utilisateurs");
        System.out.println("h - Historique des emprunts");
        System.out.println("b - Retour");
        System.out.print("Choix : ");

        switch (scanner.nextLine()) {
            case "a" -> {
                System.out.print("Nom : ");
                UserService.addUser(scanner.nextLine());
            }
            case "m" -> {
                try {
                    System.out.print("ID de l'utilisateur à modifier : ");
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.print("Nouveau nom : ");
                    String name = scanner.nextLine();
                    UserService.updateUser(id, name);
                } catch (NumberFormatException e) {
                    System.out.println("ID invalide. Veuillez entrer un nombre.");
                }
            }
            case "s" -> {
                try {
                    System.out.print("ID de l'utilisateur à supprimer : ");
                    int id = Integer.parseInt(scanner.nextLine());
                    UserService.deleteUser(id);
                } catch (NumberFormatException e) {
                    System.out.println("ID invalide. Veuillez entrer un nombre.");
                }
            }
            case "l" -> UserService.listUsers();
            case "h" -> {
                System.out.print("ID Utilisateur : ");
                try {
                    int userId = Integer.parseInt(scanner.nextLine());
                    LoanService.listLoansByUser(userId);
                } catch (NumberFormatException e) {
                    System.out.println("ID invalide. Veuillez entrer un nombre.");
                }
            }
            case "b" -> {}
            default -> System.out.println("Choix invalide.");
        }
    }

    private static void menuLoans() {
        System.out.println("\n=== MENU EMPRUNTS ===");
        System.out.println("e - Emprunter un livre");
        System.out.println("r - Retourner un livre");
        System.out.println("l - Lister tous les emprunts");
        System.out.println("a - Emprunts actifs");
        System.out.println("b - Retour");
        System.out.print("Choix : ");

        switch (scanner.nextLine()) {
            case "e" -> {
                try {
                    System.out.print("ID Livre : ");
                    int bookId = Integer.parseInt(scanner.nextLine());
                    System.out.print("ID Utilisateur : ");
                    int userId = Integer.parseInt(scanner.nextLine());
                    LoanService.borrowBook(bookId, userId);
                } catch (NumberFormatException e) {
                    System.out.println("Erreur : Veuillez entrer des nombres valides.");
                }
            }
            case "r" -> {
                try {
                    System.out.print("ID Livre : ");
                    LoanService.returnBook(Integer.parseInt(scanner.nextLine()));
                } catch (NumberFormatException e) {
                    System.out.println("Erreur : Veuillez entrer un nombre valide.");
                }
            }
            case "l" -> LoanService.listLoans();
            case "a" -> LoanService.listActiveLoans();
            case "b" -> {}
            default -> System.out.println("Choix invalide.");
        }
    }
}
