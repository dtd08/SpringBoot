package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller // 컴포넌트 스캔  // 스프링 컨테이너에 등록
public class MemberController { // 웹페이지와 연결되어 사용자 동작을 받을 컨트롤러
    private final MemberService memberService;

    @Autowired // 이 애노테이션은 컨트롤러가 생성될 때 생성자를 호출했을때, 스프링 컨테이너가 서비스와 컨트롤러를 연결시켜줌  // 한마디로 자동으로 의존관계를 설정해줌
    public MemberController(MemberService memberService) { // Dependency Injection(DI)
        this.memberService = memberService;
    }
}
