package services;

import models.User;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private static final List<User> users = new ArrayList<>();
    private static int nextId = 1;

    public static void addUser(String name) {
        users.add(new User(nextId++, name));
        System.out.println("Utilisateur ajouté.");
    }

    public static void updateUser(int id, String name) {
        User user = findById(id);
        if (user == null) {
            System.out.println("Utilisateur introuvable.");
            return;
        }
        user.setName(name);
        System.out.println("Utilisateur modifié.");
    }

    public static void deleteUser(int id) {
        User user = findById(id);
        if (user == null) {
            System.out.println("Utilisateur introuvable.");
            return;
        }
        users.remove(user);
        System.out.println("Utilisateur supprimé.");
    }

    public static void listUsers() {
        if (users.isEmpty()) {
            System.out.println("Aucun utilisateur.");
            return;
        }
        users.forEach(System.out::println);
    }

    public static User findById(int id) {
        return users.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public static void clearAll() {
        users.clear();
        nextId = 1;
    }
}
