package com.library.controller;

import com.library.model.Book;
import com.library.model.IssuedBook;
import com.library.model.User;
import com.library.service.BookService;
import com.library.service.IssuedBookService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/librarian")
public class LibrarianController {

    @Autowired
    private BookService bookService;

    @Autowired
    private IssuedBookService issuedBookService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != User.Role.LIBRARIAN) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "librarian/dashboard";
    }

    @GetMapping("/add-book")
    public String addBookPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != User.Role.LIBRARIAN) {
            return "redirect:/login";
        }
        model.addAttribute("book", new Book());
        return "librarian/add-book";
    }

    @PostMapping("/add-book")
    public String addBook(@ModelAttribute Book book, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != User.Role.LIBRARIAN) {
            return "redirect:/login";
        }

        bookService.saveBook(book);
        model.addAttribute("success", "Book added successfully!");
        model.addAttribute("book", new Book());
        return "librarian/add-book";
    }

    @GetMapping("/view-books")
    public String viewBooks(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != User.Role.LIBRARIAN) {
            return "redirect:/login";
        }

        model.addAttribute("books", bookService.getAllBooks());
        return "librarian/view-books";
    }

    @GetMapping("/issue-book")
    public String issueBookPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != User.Role.LIBRARIAN) {
            return "redirect:/login";
        }

        model.addAttribute("books", bookService.getAllBooks());
        return "librarian/issue-book";
    }

    @PostMapping("/issue-book")
    public String issueBook(@RequestParam Long bookId,
                            @RequestParam String studentName,
                            @RequestParam String studentId,
                            HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != User.Role.LIBRARIAN) {
            return "redirect:/login";
        }

        IssuedBook issuedBook = issuedBookService.issueBook(bookId, studentName, studentId);
        if (issuedBook != null) {
            model.addAttribute("success", "Book issued successfully!");
        } else {
            model.addAttribute("error", "Book not available for issue!");
        }

        model.addAttribute("books", bookService.getAllBooks());
        return "librarian/issue-book";
    }

    @GetMapping("/view-issued")
    public String viewIssuedBooks(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != User.Role.LIBRARIAN) {
            return "redirect:/login";
        }

        model.addAttribute("issuedBooks", issuedBookService.getAllIssuedBooks());
        return "librarian/view-issued";
    }

    @PostMapping("/return-book/{id}")
    public String returnBook(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != User.Role.LIBRARIAN) {
            return "redirect:/login";
        }

        issuedBookService.returnBook(id);
        return "redirect:/librarian/view-issued";
    }

}
