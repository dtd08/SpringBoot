package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest // 스프링 컨테이너와 함께 테스트 실행
@Transactional  // 테스트 전에 트랜젝션 시작, 테스트 완료 후 항상 롤백 => DB에 데이터가 남지 않아 테스트에 영향을 주지 않음
class MemberServiceIntegrationTest {
    @Autowired MemberService memberService; // 테스트 때에는 간단하게 필드에 오토 와이어를 해도 상관없다.
    @Autowired MemberRepository memberRepository; // 구현체는 config에서 자동으로 적용함

    @Test
    void 회원가입() {
        // given(주어진 상황, 기반 데이터)
        Member member = new Member();
        member.setName("spring");

        // when(실행, 검증하는 것)
        Long saveId = memberService.join(member); // 완료되면 id를 리턴함

        // then(결과, 검증부) 문법
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName()); // join의 결과로 리턴된 id가 저장한 member와 같은지 확인
    }

    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);
        // try catch문을 대신하는 문법
        // 오른쪽(-> 다음) 로직이 돌아갈 때 왼쪽 예외가 발생해야 함 (예외가 발생하지 않는다면 오류뜸)
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다."); // 메세지 확인
    }
}
