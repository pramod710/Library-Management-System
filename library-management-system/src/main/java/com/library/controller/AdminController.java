package com.library.controller;

import com.library.model.Book;
import com.library.model.User;
import com.library.service.BookService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BookService bookService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "admin/dashboard";
    }

    @GetMapping("/add-book")
    public String addBookPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }

        model.addAttribute("book", new Book());
        return "admin/add-book";
    }

    @PostMapping("/add-book")
    public String addBook(@ModelAttribute Book book, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }

        bookService.saveBook(book);
        model.addAttribute("success", "Book added successfully!");
        model.addAttribute("book", new Book());
        return "redirect:/login";
    }

    @GetMapping("/view-books")
    public String viewBooks(HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }

        model.addAttribute("books", bookService.getAllBooks());
        return "admin/view-books";
    }

    @PostMapping("/delete-book/{id}")
    public String deleteBook(@PathVariable Long id, HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != User.Role.ADMIN) {
            return "redirect:/login";
        }

        bookService.deleteBook(id);
        return "redirect:/admin/view-books";
    }
}
