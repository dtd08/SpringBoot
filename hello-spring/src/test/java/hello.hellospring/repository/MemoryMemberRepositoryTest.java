package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest { // 구현체 테스트

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @Test // 이렇게 하면 그냥 실행됨  // main 메소드처럼 쓰면 됨
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
    }
}
