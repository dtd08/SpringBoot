package hello.hellospring;

import hello.hellospring.repository.JdbcMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@Configurable
public class SpringConfig {

    private DataSource dataSource; // 스프링이 알아서 설정 파일을 보고 자체적으로 빈을 생성해줌

    @Autowired
    public SpringConfig(DataSource dataSource) { // 데이터 소스 주입
        this.dataSource = dataSource;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
        return new JdbcMemberRepository(dataSource); // (config를 제외한) 다른 코드를 건들지 않고도 구현체를 통해 인터페이스를 확장함
    }
}
