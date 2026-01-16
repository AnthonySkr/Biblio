package models;

public class User {
    private final int id;
    private String name;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }

    // Setters
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return String.format("[%d] %s", id, name);
    }
}
