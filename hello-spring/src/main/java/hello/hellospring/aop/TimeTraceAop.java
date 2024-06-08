package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect // AOP를 쓰기 위한 annotation
@Component
public class TimeTraceAop {

    @Around("execution(* hello.hellospring..*(..))") // 공통 관심사항을 타겟팅함
    // 패키지명(.하위 패키지명).클래스명(파라미터)
    // 현재 코드로 따지자면 hello.hellospring 밑에 있는 요소에다가 모두 적용함 

    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis(); // 메소드 시작 전, 현재 시간을 저장
        System.out.println("START: " + joinPoint.toString()); // 어떤 메소드를 실행하고 있는지 출력
        try {
            return joinPoint.proceed(); // 다음 메소드 진행
        } finally {
            long finish = System.currentTimeMillis(); // 메소드 종료 후 무조건 현재 시간 저장
            long timeMs = finish - start; // 메소드 시작 전에 찍은 시간과 종료 후 찍은 시간으로 걸린 시간 구함
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
        }


    }
}
