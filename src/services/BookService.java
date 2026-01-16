package services;

import models.Book;
import java.util.ArrayList;
import java.util.List;

public class BookService {

    private static final List<Book> books = new ArrayList<>();
    private static int nextId = 1;

    public static void addBook(String title, String author, String genre) {
        Book book = new Book(nextId++, title, author, genre, true);
        books.add(book);
        System.out.println("Livre ajouté.");
    }

    public static void listBooks() {
        if (books.isEmpty()) {
            System.out.println("Aucun livre.");
            return;
        }
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public static Book findById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    public static void updateBook(int id, String title, String author, String genre) {
        Book book = findById(id);
        if (book == null) {
            System.out.println("Livre introuvable.");
            return;
        }
        book.setTitle(title);
        book.setAuthor(author);
        book.setGenre(genre);
        System.out.println("Livre modifié.");
    }

    public static void deleteBook(int id) {
        Book book = findById(id);
        if (book == null) {
            System.out.println("Livre introuvable.");
            return;
        }
        if (!book.isAvailable()) {
            System.out.println("Impossible de supprimer : le livre est actuellement emprunté.");
            return;
        }
        books.remove(book);
        System.out.println("Livre supprimé.");
    }

    public static void listAvailableBooks() {
        List<Book> availableBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.isAvailable()) {
                availableBooks.add(book);
            }
        }
        if (availableBooks.isEmpty()) {
            System.out.println("Aucun livre disponible.");
            return;
        }
        System.out.println("=== Livres disponibles ===");
        for (Book book : availableBooks) {
            System.out.println(book);
        }
    }

    public static void listBorrowedBooks() {
        List<Book> borrowedBooks = new ArrayList<>();
        for (Book book : books) {
            if (!book.isAvailable()) {
                borrowedBooks.add(book);
            }
        }
        if (borrowedBooks.isEmpty()) {
            System.out.println("Aucun livre emprunté.");
            return;
        }
        System.out.println("=== Livres empruntés ===");
        for (Book book : borrowedBooks) {
            System.out.println(book);
        }
    }

    public static void searchByTitle(String title) {
        List<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                results.add(book);
            }
        }
        if (results.isEmpty()) {
            System.out.println("Aucun livre trouvé avec le titre : " + title);
            return;
        }
        System.out.println("=== Résultats de recherche (Titre) ===");
        for (Book book : results) {
            System.out.println(book);
        }
    }

    public static void searchByAuthor(String author) {
        List<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                results.add(book);
            }
        }
        if (results.isEmpty()) {
            System.out.println("Aucun livre trouvé pour l'auteur : " + author);
            return;
        }
        System.out.println("=== Résultats de recherche (Auteur) ===");
        for (Book book : results) {
            System.out.println(book);
        }
    }

    public static void searchByGenre(String genre) {
        List<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getGenre().toLowerCase().contains(genre.toLowerCase())) {
                results.add(book);
            }
        }
        if (results.isEmpty()) {
            System.out.println("Aucun livre trouvé pour le genre : " + genre);
            return;
        }
        System.out.println("=== Résultats de recherche (Genre) ===");
        for (Book book : results) {
            System.out.println(book);
        }
    }
}
