package services;

import database.DatabaseManager;
import models.User;
import java.sql.*;

public class UserService {

    private static final DatabaseManager db = DatabaseManager.getInstance();

    public static void addUser(String name) {
        String sql = "INSERT INTO users (name) VALUES (?)";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            System.out.println("Utilisateur ajouté.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
        }
    }

    public static void updateUser(int id, String name) {
        if (findById(id) == null) {
            System.out.println("Utilisateur introuvable.");
            return;
        }

        String sql = "UPDATE users SET name = ? WHERE id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            System.out.println("Utilisateur modifié.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de l'utilisateur : " + e.getMessage());
        }
    }

    public static void deleteUser(int id) {
        if (findById(id) == null) {
            System.out.println("Utilisateur introuvable.");
            return;
        }

        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Utilisateur supprimé.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'utilisateur : " + e.getMessage());
        }
    }

    public static void listUsers() {
        String sql = "SELECT * FROM users";
        try (Statement stmt = db.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            boolean hasUsers = false;
            while (rs.next()) {
                hasUsers = true;
                User user = new User(rs.getInt("id"), rs.getString("name"));
                System.out.println(user);
            }

            if (!hasUsers) {
                System.out.println("Aucun utilisateur.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des utilisateurs : " + e.getMessage());
        }
    }

    public static User findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche de l'utilisateur : " + e.getMessage());
        }
        return null;
    }
}
