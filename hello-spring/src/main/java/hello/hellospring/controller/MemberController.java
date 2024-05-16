package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller // 컴포넌트 스캔  // 스프링 컨테이너에 등록
public class MemberController { // 웹페이지와 연결되어 사용자 동작을 받을 컨트롤러
    private final MemberService memberService;

    @Autowired // 이 애노테이션은 컨트롤러가 생성될 때 생성자를 호출했을때, 스프링 컨테이너가 서비스와 컨트롤러를 연결시켜줌  // 한마디로 자동으로 의존관계를 설정해줌
    public MemberController(MemberService memberService) { // Dependency Injection(DI)
        this.memberService = memberService;
    }

    @GetMapping("/members/new") // 회원 가입  // 주로 조회할 때 get을 씀
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new") // 회원 데이터 수신  // 주로 폼에 넣어 데이터를 전달할 때 post를 씀
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";  // 홈화면으로 되돌리기
    }

    @GetMapping("/members") // 회원 목록 받고 전달하기
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList"; // 템플릿 찾기
    }
}
