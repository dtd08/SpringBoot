package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    // jpa는 EntityManager로 동작함
    // build.gradle 에서 받은 jpa 라이브러리를 보고 스프링이 데이터 소스가 포함된 EntityManager를 자동으로 생성해줌
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        // 이 메소드는 return 값이 없음
        // 메소드 안에서 insert와 setId까지 모두 다 해줌
        em.persist(member); // persist: 영구저장
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id); // em.find( 조회할 타입, 식별자(pk값) )
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        // pk가 아닌 값을 조회하는 경우 jpql이라는 객체지향 쿼리 언어를 사용해야 함(sql과 유사)
        // 보통은 테이블을 대상으로 쿼리를 날리는데 jpql은 객체(entity)를 대상으로 쿼리를 날림  // 그럼 날린 쿼리가 sql로 번역됨
        // sql처럼 컬럼이 아닌 객체(entity) 자체를 select 함
        List<Member> result = em.createQuery("select m from Member m wehre m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
