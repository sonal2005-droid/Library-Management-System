package com.example.demo;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMemberById(int id) {
        return memberRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));
    }

    public Member addMember(Member member) {
        return memberRepository.save(member);
    }

    public Member updateMember(int id, Member updated) {
        Member member = getMemberById(id);
        member.setName(updated.getName());
        member.setEmail(updated.getEmail());
        member.setPhone(updated.getPhone());
        member.setActive(updated.isActive());
        return memberRepository.save(member);
    }

    public void deleteMember(int id) {
        memberRepository.deleteById(id);
    }

    public List<Member> searchByName(String name) {
        return memberRepository.findByNameContainingIgnoreCase(name);
    }
}
