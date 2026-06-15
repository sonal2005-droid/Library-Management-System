package com.example.demo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/borrow")
@Tag(name = "📖 Borrow & Return", description = "APIs for borrowing and returning books")
public class BorrowController {

    private final BorrowService borrowService;

    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @PostMapping("/{bookId}/member/{memberId}")
    @Operation(summary = "Borrow a book", description = "Member borrows a book — sets 14 day due date automatically")
    public ResponseEntity<BorrowRecord> borrowBook(
            @PathVariable int bookId,
            @PathVariable int memberId) {
        return ResponseEntity.ok(borrowService.borrowBook(bookId, memberId));
    }

    @PutMapping("/return/{recordId}")
    @Operation(summary = "Return a book", description = "Returns a book and calculates fine if overdue (₹5/day)")
    public ResponseEntity<BorrowRecord> returnBook(@PathVariable int recordId) {
        return ResponseEntity.ok(borrowService.returnBook(recordId));
    }

    @GetMapping
    @Operation(summary = "Get all borrow records")
    public ResponseEntity<List<BorrowRecord>> getAllRecords() {
        return ResponseEntity.ok(borrowService.getAllRecords());
    }

    @GetMapping("/active")
    @Operation(summary = "Get active loans", description = "Returns all books currently borrowed and not returned")
    public ResponseEntity<List<BorrowRecord>> getActiveLoans() {
        return ResponseEntity.ok(borrowService.getActiveLoans());
    }

    @GetMapping("/member/{memberId}")
    @Operation(summary = "Get member borrow history")
    public ResponseEntity<List<BorrowRecord>> getMemberHistory(@PathVariable int memberId) {
        return ResponseEntity.ok(borrowService.getMemberHistory(memberId));
    }
}
