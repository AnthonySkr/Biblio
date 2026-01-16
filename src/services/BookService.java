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

    public static void deleteBook(int id) {
        Book book = findById(id);
        if (book == null) {
            System.out.println("Livre introuvable.");
            return;
        }
        books.remove(book);
        System.out.println("Livre supprimé.");
    }
}
