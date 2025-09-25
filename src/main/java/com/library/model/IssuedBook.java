package com.library.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "issued_books")
public class IssuedBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private String studentName;
    private String studentId;
    private LocalDate issueDate;
    private LocalDate returnDate;
    private boolean returned;

    // Constructors

    public IssuedBook() {}

    public IssuedBook(Book book, String studentName, String studentId) {
        this.book = book;
        this.studentName = studentName;
        this.studentId = studentId;
        this.issueDate = LocalDate.now();
        this.returnDate = LocalDate.now().plusDays(14);
        this.returned = false;
    }


    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public LocalDate getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public boolean isReturned() { return returned; }
    public void setReturned(boolean returned) { this.returned = returned; }

}
