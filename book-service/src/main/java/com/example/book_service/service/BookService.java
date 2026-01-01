package com.example.book_service.service;


import com.example.book_service.domain.Book;
import com.example.book_service.repo.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository repo;
    private final PricingClient pricing;

    public BookService(BookRepository repo, PricingClient pricing) {
        this.repo = repo;
        this.pricing = pricing;
    }

    public List all() {
        return repo.findAll();
    }

    public Book create(Book b) {
        repo.findByTitle(b.getTitle()).ifPresent(x -> {
            throw new IllegalArgumentException("Titre déjà existant");
        });
        return (Book) repo.save(b);
    }

    @Transactional
    public BorrowResult borrow(long id) throws Throwable {
        // verrou DB ici
        Book book = (Book) repo.findByIdForUpdate(id).orElseThrow(() -> new IllegalArgumentException("Livre introuvable"));

        book.decrementStock(); // peut lancer IllegalStateException
        double price = pricing.getPrice(id);

        return new BorrowResult(book.getId(), book.getTitle(), book.getStock(), price);
    }

    public record BorrowResult(Long id, String title, int stockLeft, double price) {}
}