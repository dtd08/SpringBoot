package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import java.util.Optional;

public class MemberService {
    // 회원 서비스 개발을 위한 회원 리포지토리 생성
    private final MemberRepository memberRepository = new MemoryMemberRepository();

    // 회원 가입
    public Long join(Member member) {
        // 같은 이름의 중복 회원X
        Optional<Member> result = memberRepository.findByName(member.getName()); // Optional 안에 Member 객체가 있는 것임
        result.ifPresent(m -> { // 만약 member에 값이 있으면 = null이 아니라 값이 있으면
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });

        memberRepository.save(member); // 리포지토리에 저장
        return member.getId(); // (임의로 설정)아이디값 반환
    }


}
