package com.example.book_service.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "book")
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=120)
    private String title;

    @Column(nullable=false, length=80)
    private String author;

    @Column(nullable=false)
    private int stock;

    public Book() {}

    public Book(String title, String author, int stock) {
        this.title = title;
        this.author = author;
        this.stock = stock;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getStock() { return stock; }

    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setStock(int stock) { this.stock = stock; }

    public void decrementStock() {
        if (stock <= 0) throw new IllegalStateException("Plus d’exemplaires");
        stock--;
    }
}