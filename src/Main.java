import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        boolean running = true;

        while (running) {
            afficherMenuPrincipal();
            String choix = scanner.nextLine();

            switch (choix) {
                case "1" -> menuLivres();
                case "2" -> menuUtilisateurs();
                case "3" -> menuEmprunts();
                case "0" -> {
                    System.out.println("Au revoir.");
                    running = false;
                }
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private static void afficherMenuPrincipal() {
        System.out.println("\n===== GESTIONNAIRE DE BIBLIOTHÈQUE =====");
        System.out.println("1 - Gestion des livres");
        System.out.println("2 - Gestion des utilisateurs");
        System.out.println("3 - Emprunts / retours");
        System.out.println("0 - Quitter");
        System.out.print("Choix : ");
    }

    private static void menuLivres() {
        boolean retour = false;

        while (!retour) {
            System.out.println("\n--- Gestion des livres ---");
            System.out.println("a - Ajouter un livre");
            System.out.println("m - Modifier un livre");
            System.out.println("s - Supprimer un livre");
            System.out.println("l - Lister les livres");
            System.out.println("r - Rechercher un livre");
            System.out.println("b - Retour");
            System.out.print("Choix : ");

            String choix = scanner.nextLine();

            switch (choix) {
                case "a" -> System.out.println("Ajout d’un livre (à implémenter)");
                case "m" -> System.out.println("Modification d’un livre (à implémenter)");
                case "s" -> System.out.println("Suppression d’un livre (à implémenter)");
                case "l" -> System.out.println("Liste des livres (à implémenter)");
                case "r" -> System.out.println("Recherche de livres (à implémenter)");
                case "b" -> retour = true;
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private static void menuUtilisateurs() {
        boolean retour = false;

        while (!retour) {
            System.out.println("\n--- Gestion des utilisateurs ---");
            System.out.println("a - Ajouter un utilisateur");
            System.out.println("m - Modifier un utilisateur");
            System.out.println("s - Supprimer un utilisateur");
            System.out.println("l - Lister les utilisateurs");
            System.out.println("h - Historique des emprunts");
            System.out.println("b - Retour");
            System.out.print("Choix : ");

            String choix = scanner.nextLine();

            switch (choix) {
                case "a" -> System.out.println("Ajout utilisateur (à implémenter)");
                case "m" -> System.out.println("Modification utilisateur (à implémenter)");
                case "s" -> System.out.println("Suppression utilisateur (à implémenter)");
                case "l" -> System.out.println("Liste utilisateurs (à implémenter)");
                case "h" -> System.out.println("Historique des emprunts (à implémenter)");
                case "b" -> retour = true;
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private static void menuEmprunts() {
        boolean retour = false;

        while (!retour) {
            System.out.println("\n--- Emprunts / retours ---");
            System.out.println("e - Emprunter un livre");
            System.out.println("r - Retourner un livre");
            System.out.println("l - Lister les emprunts");
            System.out.println("b - Retour");
            System.out.print("Choix : ");

            String choix = scanner.nextLine();

            switch (choix) {
                case "e" -> System.out.println("Emprunt (à implémenter)");
                case "r" -> System.out.println("Retour (à implémenter)");
                case "l" -> System.out.println("Liste des emprunts (à implémenter)");
                case "b" -> retour = true;
                default -> System.out.println("Choix invalide.");
            }
        }
    }
}
