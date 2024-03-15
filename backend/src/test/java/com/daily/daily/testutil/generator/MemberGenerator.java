package com.daily.daily.testutil.generator;

import com.daily.daily.member.domain.Member;
import com.daily.daily.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MemberGenerator {
    @Autowired
    MemberRepository memberRepository;

    public List<Member> generate(int count) {
        List<Member> members = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            Member member = Member.builder().build();
            members.add(memberRepository.save(member));
        }

        return members;
    }
}
