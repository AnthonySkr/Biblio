package models;

import java.time.LocalDate;

public class Loan {
    private final int id;
    private final int bookId;
    private final int userId;
    private final LocalDate loanDate;
    private LocalDate returnDate;

    public Loan(int id, int bookId, int userId, LocalDate loanDate, LocalDate returnDate) {
        this.id = id;
        this.bookId = bookId;
        this.userId = userId;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    // Getters
    public int getId() { return id; }
    public int getBookId() { return bookId; }
    public int getUserId() { return userId; }
    public LocalDate getLoanDate() { return loanDate; }
    public LocalDate getReturnDate() { return returnDate; }

    // Setters
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