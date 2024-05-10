package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest { // 구현체 테스트

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach // 각 메소드가 끝나고 나서 호출됨  // save 끝나고 요거, findById 끝나고 요거
    public void afterEach() {
        repository.clearStore();
    }

    @Test // 이렇게 하면 그냥 실행됨  // main 메소드처럼 쓰면 됨  // Test가 좋은 점은 전체 클래스 레벨 혹은 각 테스트 별로 테스트할 수 있기 때문임
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member); // save 기능 테스트
        // 저장할 때 id가 세팅되었으니 id로 찾기 가능
        Member result = repository.findById(member.getId()).get(); // get() 은 Optional의 값을 꺼내오는 메소드임  // 좋은 코드는 아니지만 테스트니 그냥 씀
        // 검증 => 새로 꺼낸 애랑 db에서 꺼낸 애가 같으면 참
        // System.out.println("result = " + (result == member));

        // 위의 코드를 해주는 Assertions라는게 있음
        // 같으면 초록불(아무것도 안뜸), 다르면 오류 뜸
        // Assertions.assertEquals(member, result); // Assertions.assertEquals(기대하는 값, 찾는 값)

        // 위의 코드는 jUnit의 Asserstions고,
        // assertionsj의 Assertions가 있는데 이게 더 보기 쉽고 이해하기 좋음
        assertThat(member).isEqualTo(result); // static import하면 좀 더 편하게 쓸 수 있음
        // 실무에서 이 방법을 build 툴이랑 엮어서 build 오류 테스트 때 통과하지 못하면 다음 단계로 못넘어가 게 막음

    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1); // 회원1 가입

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2); // 회원2 가입

        Member result = repository.findByName("spring1").get(); // name으로 찾은 회원을 result에 저장

        assertThat(result).isEqualTo(member1); // 올바른 값과 같은지 비교

    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring1");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }

    // 모든 테스트 코드를 작성 후 전체 테스트를 돌려보면 오류가 날 것임
    // 그 이유는 테스트의 실행 순서는 보장되지 않아 순서 상관 없이 메소드별로 따로 동작하게만 설계해야 함
    // 그런데 우리의 코드는 findAll()이 실행되었을 때, 앞서 spring1, spring2가 저장되어 버림
    // 때문에 findByName() 할 때 findAll()에 있던 값이 와 버린 것임

    // 이런 문제는 어떻게 해결하나? => 각 테스트가 끝난 후 값을 초기화 시켜야 함
    // 맨 위에 afterEach() 라는 메소드를 만들고 @AfterEach 를 붙여줌
    // 이 @AfterEach는 각 test 메소드가 끝날 때마다 호출됨
    // MemoryMemberRepository에 clearStore()라는 저장된 회원들을 싹 지워주는 메소드를 만들고 afterEach() 에서 실행시킴
    // 테스트는 서로 그 순서와 관계없이, 의존관계 없이 설계되어야 한다 <= 굉장히 중요한 내용임

    // 방금 우리는 먼저 메모리 멤버 리포지토리를 다 만들어 놓고 테스트 했지만,
    // 반대로 틀(테스트)을 먼저 만들고 거기에 맞춰 개발(구현)하는 방법도 있음
    // 이를 테스트 주도 개발(TDD)이라고 함
}
