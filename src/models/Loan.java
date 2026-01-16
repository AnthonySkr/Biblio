package models;

import java.time.LocalDate;

public class Loan {
    private int id;
    private int bookId;
    private int userId;
    private LocalDate loanDate;
    private LocalDate returnDate;

    public Loan(int id, int bookId, int userId, LocalDate loanDate) {
        this.id = id;
        this.bookId = bookId;
        this.userId = userId;
        this.loanDate = loanDate;
        this.returnDate = null;
    }

    public Loan(int bookId, int userId) {
        this.id = 0; // Sera généré par la base de données ou le service
        this.bookId = bookId;
        this.userId = userId;
        this.loanDate = LocalDate.now();
        this.returnDate = null;
    }

    // Getters
    public int getId() { return id; }
    public int getBookId() { return bookId; }
    public int getUserId() { return userId; }
    public LocalDate getLoanDate() { return loanDate; }
    public LocalDate getReturnDate() { return returnDate; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public boolean isReturned() {
        return returnDate != null;
    }

    @Override
    public String toString() {
        return String.format("[%d] Livre %d - Utilisateur %d - Emprunté le %s%s",
                id, bookId, userId, loanDate,
                isReturned() ? " - Retourné le " + returnDate : " - En cours");
    }

    public void returnBook() {
        this.returnDate = LocalDate.now();
    }
}