package thespeace.springbasic2.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import thespeace.springbasic2.AppConfig;
import thespeace.springbasic2.member.MemberRepository;
import thespeace.springbasic2.member.MemberServiceImpl;
import thespeace.springbasic2.order.OrderServiceImpl;

import static org.assertj.core.api.Assertions.*;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        //모두 같은 인스턴스를 참고하고 있다.
        System.out.println("memberService -> memberRepository1 = " + memberRepository1);
        System.out.println("orderService -> memberRepository2 = " + memberRepository2);
        System.out.println("memberRepository = " + memberRepository);

        //모두 같은 인스턴스를 참고하고 있다.
        assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
    }
}
/**
 *  -@Configuration과 싱글톤
 *   `AppConfig.java`의 코드를 살펴보자.
 *      memberService 빈을 만드는 코드를 보면 memberRepository()를 호출한다.
 *          이 메서드를 호출하면 new MemoryMemberRepository()를 호출한다.
 *      orderService 빈을 만드는 코드도 동일하게 memberRepository()를 호출한다.
 *          이 메서드를 호출하면 new MemoryMemberRepository()를 호출한다.
 *
 *   결과적으로 각각 다른 2개의 MemoryMemberRepository가 생성되면서 싱글톤이 깨지는 것 처럼 보인다. 스프링 컨테이너는 이 문제를 어떻게 해결할까..?
 *
 *   스프링 컨테이너가 각각 @Bean을 호출해서 스프링 빈을 생성한다.
 *   그래서 memberRepository()는 다음과 같이 총 3번이 호출되어야 하는 것 아닐까?
 *      1. 스프링 컨테이너가 스프링 빈에 등록하기 위해 @Bean이 붙어있는 `memberRepository()` 호출
 *      2. memberService() 로직에서 `memberRepository()` 호출
 *      3. orderService() 로직에서 `memberRepository()` 호출
 *
 *   그런데 출력 결과는 모두 1번만 호출된다.
 *   스프링은 Bean이 싱글톤이 되도록 보장해주어야 한다. 그걸 위해 스프링은 `바이트코드 조작 라이브러리`를 사용한다.
 */
