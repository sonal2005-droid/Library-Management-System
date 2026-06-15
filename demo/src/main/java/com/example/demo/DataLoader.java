package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public DataLoader(BookRepository bookRepository,
                      MemberRepository memberRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // Load sample books
        if (bookRepository.count() == 0) {
            bookRepository.save(createBook("Clean Code", "Robert C. Martin"));
            bookRepository.save(createBook("The Pragmatic Programmer", "Andrew Hunt"));
            bookRepository.save(createBook("Design Patterns", "Gang of Four"));
            bookRepository.save(createBook("Introduction to Algorithms", "Thomas H. Cormen"));
            bookRepository.save(createBook("Database Management Systems", "Korth"));
            bookRepository.save(createBook("Operating System Concepts", "Silberschatz"));
            bookRepository.save(createBook("Computer Networks", "Andrew Tanenbaum"));
            bookRepository.save(createBook("Artificial Intelligence", "Stuart Russell"));
            System.out.println("✅ Sample books loaded!");
        }

        // Load sample members
        if (memberRepository.count() == 0) {
            memberRepository.save(createMember("Sonal Sarkar", "sonal@jadavpur.edu", "9876543210"));
            memberRepository.save(createMember("Rahul Das", "rahul@jadavpur.edu", "9876543211"));
            memberRepository.save(createMember("Priya Ghosh", "priya@jadavpur.edu", "9876543212"));
            memberRepository.save(createMember("Arjun Mehta", "arjun@jadavpur.edu", "9876543213"));
            System.out.println("✅ Sample members loaded!");
        }
    }

    private Book createBook(String title, String author) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setAvailable(true);
        return book;
    }

    private Member createMember(String name, String email, String phone) {
        Member member = new Member();
        member.setName(name);
        member.setEmail(email);
        member.setPhone(phone);
        member.setActive(true);
        return member;
    }
}
