package database;

import java.sql.*;
import java.util.logging.Logger;
import java.util.logging.Level;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:library.db";
    private static final Logger LOGGER = Logger.getLogger(DatabaseManager.class.getName());
    private static DatabaseManager instance;
    private Connection connection;

    private DatabaseManager() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            createTables();
            LOGGER.info("Connecté à la base de données.");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur de connexion à la base de données.", e);
        }
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    private void createTables() {
        createBooksTable();
        createUsersTable();
        createLoansTable();
    }

    private void createBooksTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS books (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT NOT NULL,
                author TEXT NOT NULL,
                genre TEXT NOT NULL,
                is_available INTEGER DEFAULT 1
            )
            """;
        executeUpdate(sql, "Table books créée");
    }

    private void createUsersTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                email TEXT UNIQUE NOT NULL
            )
            """;
        executeUpdate(sql, "Table users créée");
    }

    private void createLoansTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS loans (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                book_id INTEGER NOT NULL,
                user_id INTEGER NOT NULL,
                loan_date TEXT NOT NULL,
                return_date TEXT,
                FOREIGN KEY (book_id) REFERENCES books(id),
                FOREIGN KEY (user_id) REFERENCES users(id)
            )
            """;
        executeUpdate(sql, "Table loans créée");
    }

    private void executeUpdate(String sql, String successMessage) {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            LOGGER.info(successMessage);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de l'exécution de la requête", e);
        }
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                LOGGER.info("Connexion à la base de données fermée");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la fermeture de la connexion", e);
        }
    }
}
