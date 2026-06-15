package com.example.demo;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowService {

    private final BorrowRepository borrowRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public BorrowService(BorrowRepository borrowRepository,
                         BookRepository bookRepository,
                         MemberRepository memberRepository) {
        this.borrowRepository = borrowRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    public BorrowRecord borrowBook(int bookId, int memberId) {
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new RuntimeException("Book not found"));
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new RuntimeException("Member not found"));

        if (!book.isAvailable())
            throw new RuntimeException("Book is not available");

        book.setAvailable(false);
        bookRepository.save(book);

        BorrowRecord record = new BorrowRecord();
        record.setBook(book);
        record.setMember(member);
        record.setBorrowDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(14)); // 2 weeks
        return borrowRepository.save(record);
    }

    public BorrowRecord returnBook(int recordId) {
        BorrowRecord record = borrowRepository.findById(recordId)
            .orElseThrow(() -> new RuntimeException("Borrow record not found"));

        record.setReturned(true);
        record.setReturnDate(LocalDate.now());

        Book book = record.getBook();
        book.setAvailable(true);
        bookRepository.save(book);

        double fine = record.calculateFine();
        if (fine > 0) {
            record.setFineStatus(BorrowRecord.FineStatus.PENDING);
        }

        return borrowRepository.save(record);
    }

    public List<BorrowRecord> getAllRecords() {
        return borrowRepository.findAll();
    }

    public List<BorrowRecord> getMemberHistory(int memberId) {
        return borrowRepository.findByMemberId(memberId);
    }

    public List<BorrowRecord> getActiveLoans() {
        return borrowRepository.findByReturned(false);
    }
}
