package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BorrowRepository extends JpaRepository<BorrowRecord, Integer> {
    List<BorrowRecord> findByMemberId(int memberId);
    List<BorrowRecord> findByBookId(int bookId);
    List<BorrowRecord> findByReturned(boolean returned);
}