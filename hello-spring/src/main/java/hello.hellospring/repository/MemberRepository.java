package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

// db가 어느 기술을 쓸지 확실히 정해지지 않았다는 가정하에 어느 기술을 쓰든 쉽게 변경 가능하도록 interface로 일단 구현
public interface MemberReopsitory { // 회원 객체 저장하는 저장소  
    Member save(Member Member); // 회원 저장 기능  // 회원을 저장하면 저장된 회원 반환
    // Optional은 간단히 말해 null을 이 Optional로 한 번 감싸서 처리하는 방법임
    Optional<Member> findById(Long id); // 아이디로 회원 찾기
    Optional<Member> findByName(String name); // 이름으로 회원 찾기
    List<Member> findAll(); // 모든 회원 조회
}
