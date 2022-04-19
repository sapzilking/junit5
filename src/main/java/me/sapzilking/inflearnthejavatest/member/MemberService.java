package me.sapzilking.inflearnthejavatest.member;

import me.sapzilking.inflearnthejavatest.domain.Member;
import me.sapzilking.inflearnthejavatest.domain.Study;

import java.util.Optional;

public interface MemberService {

    Optional<Member> findById(Long memberId);

    void validate(Long memberId);

    void notify(Study newstudy);

    void notify(Member member);
}
