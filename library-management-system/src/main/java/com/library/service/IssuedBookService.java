package com.library.service;

import com.library.model.Book;
import com.library.model.IssuedBook;
import com.library.repository.IssuedBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssuedBookService {

    @Autowired
    private IssuedBookRepository issuedBookRepository;

    @Autowired
    private BookService bookService;

    public IssuedBook issueBook(Long bookId, String studentName, String studentId) {
        Book book = bookService.findById(bookId);
        if (book != null && book.getAvailableQuantity() > 0) {
            book.setAvailableQuantity(book.getAvailableQuantity() -1);
            bookService.saveBook(book);

            IssuedBook issuedBook = new IssuedBook(book, studentName,studentId);
            return issuedBookRepository.save(issuedBook);
        }
        return null;
    }

    public void returnBook(Long issuedBookId) {
        IssuedBook issuedBook = issuedBookRepository.findById(issuedBookId).orElse(null);
        if (issuedBook != null && !issuedBook.isReturned()) {
            issuedBook.setReturned(true);
            issuedBookRepository.save(issuedBook);

            Book book = issuedBook.getBook();
            book.setAvailableQuantity(book.getAvailableQuantity() +1);
            bookService.saveBook(book);
        }
    }

    public List<IssuedBook> getAllIssuedBooks() {
        return issuedBookRepository.findByReturnedFalse();
    }

    public List<IssuedBook> getAllIssuedBooksHistory() {
        return issuedBookRepository.findAll();
    }
}
