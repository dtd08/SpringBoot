package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    
    // 이 친구는 구현체가 필요 없음
    // 스프링이 알아서 구현체를 만들어 빈 등록까지 해주기 때문
    @Override
    Optional<Member> findByName(String name);
}
