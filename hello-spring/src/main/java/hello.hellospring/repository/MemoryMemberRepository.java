package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberReopsitory { // interface 구현체  // 실질적인 동작과 값이 구현됨

    private static Map<Long, Member> store = new HashMap<>(); // id와 회원을 저장하는 map
    private static long sequence = 0L; // id를 만들어주는 sequence

    @Override
    public Member save(Member member) { // 회원을 저장하면
        member.setId(++sequence); // 시퀀스 값을 하나 올려 id 값으로 저장하고
        store.put(member.getId(), member); // store에다가 id를 키 값으로 한 member 객체를 저장해줌
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); // null 값을 감싸서 반환해 클라이언트에서 어떤 동작을 할 수 있음
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream() // Lambda
                .filter(member -> member.getName().equals(name)) // 루프를 돌리면서 현재 member의 name이 파라미터의 name과 같은지 확인
                .findAny(); // 같은 경우에만 필터링 되고, 하나 찾는 순간 그냥 반환됨  // 결과는 Optional로 들어감
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); // store에 있는 모든 값들(member들)을 arrayList로 반환함
    }

    public void clearStore() { // store를 싹 비워주는 메소드
        store.clear();
    }
}
