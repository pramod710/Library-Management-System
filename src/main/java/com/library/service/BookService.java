// src/main/java/com/library/service/BookService.java
package com.library.service;

import com.library.model.Book;
import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book saveBook(Book book) {
        // Ensure availableQuantity is set correctly for new books
        if (book.getId() == null) {
            // New book - set available quantity equal to total quantity
            book.setAvailableQuantity(book.getQuantity());
        } else {
            // Existing book - update available quantity based on quantity change
            Book existingBook = bookRepository.findById(book.getId()).orElse(null);
            if (existingBook != null) {
                int quantityDifference = book.getQuantity() - existingBook.getQuantity();
                book.setAvailableQuantity(existingBook.getAvailableQuantity() + quantityDifference);
            }
        }
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public List<Book> searchBooks(String query) {
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(query, query);
    }

    public Book updateBookQuantity(Long bookId, int newQuantity) {
        Book book = findById(bookId);
        if (book != null) {
            int quantityDifference = newQuantity - book.getQuantity();
            book.setQuantity(newQuantity);
            book.setAvailableQuantity(book.getAvailableQuantity() + quantityDifference);
            return bookRepository.save(book);
        }
        return null;
    }
}