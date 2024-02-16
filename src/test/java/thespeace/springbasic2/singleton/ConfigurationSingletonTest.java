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

    @Test void configurationDeep() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        //AppConfig도 스프링 빈으로 등록된다.
        AppConfig bean = ac.getBean(AppConfig.class);

        System.out.println("bean = " + bean.getClass());
        //출력: bean = class hello.core.AppConfig$$EnhancerBySpringCGLIB$$bd479d70
        //클래스 명에 xxxCGLIB가 붙으면서 상당히 복잡해진 것을 볼 수 있다. 이것은 내가 만든 클래스가 아니라
        //스프링이 CGLIB라는 바이트코드 조작 라이브러리를 사용해서 AppConfig 클래스를 상속받은 임의의 다른 클래스를 만들고, 그 다른 클래스를 스프링 빈으로 등록한 것이다!
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
 *
 *
 *  -@Configuration과 바이트코드 조작의 마법
 *   스프링 컨테이너는 싱글톤 레지스트리다. 따라서 스프링 빈이 싱글톤이 되도록 보장해주어야 한다.
 *   그런데 스프링이 자 바 코드까지 어떻게 하기는 어렵다. 저 자바 코드를 보면 분명 3번 호출되어야 하는 것이 맞다.
 *   그래서 스프링은 클래스의 바이트코드를 조작하는 라이브러리를 사용한다.
 *   모든 비밀은 @Configuration을 적용한 AppConfig에 있다.
 *
 *   AppConfig@CGLIB 예상 코드
 *      ```java
 *      @Bean
 *      public MemberRepository memberRepository() {
 *          if (memoryMemberRepository가 이미 스프링 컨테이너에 등록되어 있으면?) {
 *              return 스프링 컨테이너에서 찾아서 반환;
 *          } else { //스프링 컨테이너에 없으면
 *              기존 로직을 호출해서 MemoryMemberRepository를 생성하고 스프링 컨테이너에 등록
 *              return 반환
 *          }
 *      }
 *      ```
 *      @Bean이 붙은 메서드마다 이미 스프링 빈이 존재하면 존재하는 빈을 반환하고, 스프링 빈이 없으면 생성해서 스프링 빈으로 등록하고 반환하는 코드가 동적으로 만들어진다.
 *      덕분에 싱글톤이 보장되는 것이다.
 *
 *   참고 : AppConfig@CGLIB는 AppConfig의 자식 타입이므로, AppConfig 타입으로 조회 할 수 있다.
 */
