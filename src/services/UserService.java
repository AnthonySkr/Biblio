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
}
