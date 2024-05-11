package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {
    // 회원 서비스 개발을 위한 회원 리포지토리 생성
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository; // Dependency Injection(DI) : 외부에서 리포지토리를 넣어줌
    }

    // 회원 가입
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검사
        memberRepository.save(member); // 리포지토리에 저장
        return member.getId(); // (임의로 설정)아이디값 반환
    }

    // 중복 확인 로직
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()) // Optional로 반환되기 때문에 바로 ifPresent 같은 메소드를 쓸 수 있음
                .ifPresent(m -> { // 만약 member에 값이 있으면 = null이 아니라 값이 있으면 예외처리
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    // 전체 회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 한 명만 조회
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}

// 리포지토리의 이름들은 단순 저장/조회에 가깝다면
// 서비스는 좀 더 비즈니스적인 용어들을 씀 (소통을 위해)

// 한마디로 서비스는 보통 비즈니스에 의존적으로 설계하고
// 리포지토리의 경우는 좀 더 단순한, 기계적인? 용어를 쓴다.
