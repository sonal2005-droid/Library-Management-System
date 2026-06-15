package com.example.demo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "borrow_records")
public class BorrowRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    private boolean returned = false;

    @Enumerated(EnumType.STRING)
    private FineStatus fineStatus = FineStatus.NONE;

    public enum FineStatus { NONE, PENDING, PAID }

    public BorrowRecord() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public boolean isReturned() { return returned; }
    public void setReturned(boolean returned) { this.returned = returned; }
    public FineStatus getFineStatus() { return fineStatus; }
    public void setFineStatus(FineStatus fineStatus) { this.fineStatus = fineStatus; }

    public double calculateFine() {
        if (returned && returnDate != null && returnDate.isAfter(dueDate)) {
            long daysLate = dueDate.until(returnDate).getDays();
            return daysLate * 5.0; // ₹5 per day fine
        }
        if (!returned && LocalDate.now().isAfter(dueDate)) {
            long daysLate = dueDate.until(LocalDate.now()).getDays();
            return daysLate * 5.0;
        }
        return 0.0;
    }
}