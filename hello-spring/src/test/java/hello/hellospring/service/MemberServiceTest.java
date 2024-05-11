package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {
    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        // MemberService와 같은 리포지토리를 사용하기 위한 로직
        memberRepository = new MemoryMemberRepository(); // 매 테스트마다 리포지토리를 새로 생성함
        memberService = new MemberService(memberRepository); // 그 리포지토리를 MemberService와 공유함
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore(); // 테스트가 끝날 때마다 리포지토리 클리어
    }

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

        /* try { // try catch 로 예외 잡기
            memberService.join(member2);
            fail("예외가 발생해야 합니다."); // 예외 잡기 실패
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.hgjgj"); // MemberService 에서 만든 예외처리 문과 같아야 함  // 현재 실행하면 오류 발생하는게 맞음
            // 되게 쓰기 애매한 try catch
        } */

        // then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다."); // 메세지 확인
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}

// 테스트 코드는 빌드에 들어가지 않기 때문에 한글로 써도 상관없음
