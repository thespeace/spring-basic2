package thespeace.springbasic2.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import thespeace.springbasic2.AppConfig;
import thespeace.springbasic2.member.MemberService;

import static org.assertj.core.api.Assertions.*;

//스프링 없는 순수한 DI 컨테이너 테스트
public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();

        //1.조희: 호출할 때 마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();

        //2.조희: 호출할 때 마다 객체를 생성
        MemberService memberService2 = appConfig.memberService();

        //3.참조값이 다른 것을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        //4.memberService1 != memberService2
        assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    public void singletonServiceTest() {
        //private으로 생성자를 막아두었다. 컴파일 오류가 발생한다.
//        new SingletonService();

        //1. 조회: 호출할 때 마다 같은 객체를 반환
        SingletonService singletonService1 = SingletonService.getInstance();

        //2. 조회: 호출할 때 마다 같은 객체를 반환
        SingletonService singletonService2 = SingletonService.getInstance();

        //참조값이 같은 것을 확인
        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);

        //singletonService1 == singletonService2
        assertThat(singletonService1).isSameAs(singletonService2);
        singletonService1.logic();

        //참고: 싱글톤 패턴을 구현하는 방법은 여러가지가 있다. 여기서는 객체를 미리 생성해두는 가장 단순하고 안전한 방법을 선택했다.
    }
}

/**
 *  -웹 애플리케이션과 싱글톤
 *   스프링은 태생이 기업용 온라인 서비스 기술을 지원하기 위해 탄생했다.
 *   대부분의 스프링 애플리케이션은 웹 애플리케이션이다. 물론 웹이 아닌 애플리케이션 개발도 얼마든지 개발할 수 있다.
 *   웹 애플리케이션은 보통 여러 고객이 동시에 요청을 한다.
 *   우리가 만들었던 스프링 없는 순수한 DI 컨테이너인 AppConfig는 요청을 할 때 마다 객체를 새로 생성한다.
 *   고객 트래픽이 초당 100이 나오면 초당 100개 객체가 생성되고 소멸된다! -> 메모리 낭비가 심하다.
 *   해결방안은 해당 객체가 딱 1개만 생성되고, 공유하도록 설계하면 된다. -> 싱글톤 패턴
 *
 *
 *
 *  -싱글톤 패턴 문제점
 *   싱글톤 패턴을 적용하면 고객의 요청이 올 때 마다 객체를 생성하는 것이 아니라, 이미 만들어진 객체를 공유해서 효율 적으로 사용할 수 있다.
 *   하지만 싱글톤 패턴은 다음과 같은 수 많은 문제점들을 가지고 있다.
 *
 *      싱글톤 패턴을 구현하는 코드 자체가 많이 들어간다.
 *      의존관계상 클라이언트가 구체 클래스에 의존한다.
 *      DIP를 위반한다.
 *      클라이언트가 구체 클래스에 의존해서 OCP 원칙을 위반할 가능성이 높다.
 *      테스트하기 어렵다.
 *      내부 속성을 변경하거나 초기화 하기 어렵다.
 *      private 생성자로 자식 클래스를 만들기 어렵다.
 *      결론적으로 유연성이 떨어진다.
 *      안티패턴으로 불리기도 한다.
 *
 *   해결방법 : `싱글톤 컨테이너`
 */