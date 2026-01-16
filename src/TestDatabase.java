import database.DatabaseManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestDatabase {
    public static void main(String[] args) {
        System.out.println("=== Test de DatabaseManager ===\n");

        // 1. Obtenir l'instance
        DatabaseManager dbManager = DatabaseManager.getInstance();
        System.out.println("✓ Instance créée");

        // 2. Vérifier la connexion
        Connection conn = dbManager.getConnection();
        if (conn != null) {
            System.out.println("✓ Connexion établie");
        }

        // 3. Vérifier que les tables existent
        try (Statement stmt = conn.createStatement()) {
            // Tester la table books
            ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='books'");
            if (rs.next()) {
                System.out.println("✓ Table 'books' existe");
            }

            // Tester la table users
            rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='users'");
            if (rs.next()) {
                System.out.println("✓ Table 'users' existe");
            }

            // Tester la table loans
            rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='loans'");
            if (rs.next()) {
                System.out.println("✓ Table 'loans' existe");
            }

            System.out.println("\n✓ Tous les tests réussis !");

        } catch (Exception e) {
            System.err.println("✗ Erreur : " + e.getMessage());
        } finally {
            dbManager.close();
        }
    }
}