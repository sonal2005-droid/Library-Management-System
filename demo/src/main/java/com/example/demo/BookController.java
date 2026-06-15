package com.example.demo;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable int id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@Valid @RequestBody Book book) {
        return ResponseEntity.ok(bookService.addBook(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @Valid @RequestBody Book book) {
        return ResponseEntity.ok(bookService.updateBook(id, book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted successfully");
    }

    
    @GetMapping("/search")
@Operation(summary = "Search books", description = "Search by title, author, or availability")
public ResponseEntity<?> search(
        @RequestParam(required = false) String title,
        @RequestParam(required = false) String author,
        @RequestParam(required = false) Boolean available) {

    if (title != null && author != null) {
        return ResponseEntity.ok(
            bookService.getAllBooks().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase()))
                .filter(b -> b.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .toList()
        );
    }
    if (title != null) return ResponseEntity.ok(bookService.searchByTitle(title));
    if (author != null) return ResponseEntity.ok(bookService.searchByAuthor(author));
    if (available != null) return ResponseEntity.ok(
        available ? bookService.getAvailableBooks() : bookService.getAllBooks()
    );
    return ResponseEntity.ok(bookService.getAllBooks());
}

    @GetMapping("/available")
    public ResponseEntity<List<Book>> getAvailableBooks() {
        return ResponseEntity.ok(bookService.getAvailableBooks());
    }
}