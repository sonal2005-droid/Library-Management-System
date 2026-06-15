package com.example.demo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/members")
@Tag(name = "👤 Member Management", description = "APIs for managing library members")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    public MemberController(MemberService memberService,MemberRepository memberRepository) {
        this.memberService = memberService;
        this.memberRepository=memberRepository;
    }

    @GetMapping
    @Operation(summary = "Get all members")
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get member by ID")
    public ResponseEntity<Member> getMember(@PathVariable int id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    @PostMapping
    @Operation(summary = "Register a new member")
    public ResponseEntity<Member> addMember(@Valid @RequestBody Member member) {
        return ResponseEntity.ok(memberService.addMember(member));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update member details")
    public ResponseEntity<Member> updateMember(@PathVariable int id, @Valid @RequestBody Member member) {
        return ResponseEntity.ok(memberService.updateMember(id, member));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a member")
    public ResponseEntity<String> deleteMember(@PathVariable int id) {
        memberService.deleteMember(id);
        return ResponseEntity.ok("Member deleted successfully");
    }

    @GetMapping("/search")
@Operation(summary = "Search members", description = "Search by name or active status")
public ResponseEntity<?> search(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) Boolean active) {
    if (name != null) return ResponseEntity.ok(memberService.searchByName(name));
    if (active != null) return ResponseEntity.ok(
        memberRepository.findByActive(active)
    );
    return ResponseEntity.ok(memberService.getAllMembers());
}
}
