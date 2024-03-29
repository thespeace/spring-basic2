package thespeace.springbasic2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import thespeace.springbasic2.discount.DiscountPolicy;
import thespeace.springbasic2.discount.FixDiscountPolicy;
import thespeace.springbasic2.discount.RateDiscountPolicy;
import thespeace.springbasic2.member.MemberRepository;
import thespeace.springbasic2.member.MemberService;
import thespeace.springbasic2.member.MemberServiceImpl;
import thespeace.springbasic2.member.MemoryMemberRepository;
import thespeace.springbasic2.order.OrderService;
import thespeace.springbasic2.order.OrderServiceImpl;

@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService"); //싱글톤 확인용
        return new MemberServiceImpl(memberRepository()); //생성자 주입.
    }

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository"); //싱글톤 확인용
        return new MemoryMemberRepository(); //중복 제거, 다른 구현체로 변경할 때 한 부분만 변경하면 된다.
    }

    @Bean
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService"); //싱글톤 확인용
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy(); //새로운 구조와 할인 정책 변경.
        return new RateDiscountPolicy();
    }
}


/**
 * -AppConfig
 *  애플리케이션의 실제 동작에 필요한 `구현 객체를 생성`한다.
 *  생성한 객체 인스턴스의 참조(래퍼런스)를 `생성자를 통해 주입(연결)`해준다.
 *  즉 객체의 생성과 연결은 `AppConfig`가 담당한다.
 *  이를 통해 `DIP 원칙이 완성`되었고, `관심사의 분리`를 이뤘다.
 *  AppConfig에는 `중복`은 없애고, `역할`에 따른 `구현`이 잘 보여야 한다(설계와 구성 정보가 그대로 드러나게).
 *  이를 통해 `구성 영역(AppConfig)`만 변경하면 `사용 영역`의 클라이언트 코드를 전혀 변경하지 않고도 기능을 확장할 수 있게 된다.
 *
 *
 *
 * -IoC, DI, 그리고 컨테이너
 *
 *  1.제어의 역전 IoC(Inversion of Control)
 *    기존 프로그램은 클라이언트 구현 객체가 스스로 필요한 서버 구현 객체를 생성하고, 실행했다. 한마디로 구현 객체가 프로그램의 제어 흐름을 스스로 조종했다. 개발자 입장에서는 자연스러운 흐름이다.
 *    반면에 `AppConfig.java`가 등장한 이후에 구현 객체는 자신의 로직을 실행하는 역할만 담당한다. 프로그램의 제어 흐름은 이제 AppConfig가 가져간다.
 *    예를 들어서 `OrderServiceImpl`은 필요한 인터페이스들을 호출하지만 어던 구현 객체들이 실행될지 모른다.
 *    프로그램에 대한 제어 흐름에 대한 권한은 모두 `AppConfig`가 가지고 있다. 심지어 `OrderServiceImpl`도 AppConfig가 생성한다.
 *    그리고 AppConfig는 `OrderServiceImpl`이 아닌 OrderService 인터페이스의 다른 구현 객체를 생성하고 실핼 할 수도 있다. 그런 사실도 모른채 `OrderServiceImpl`은 묵묵히 자신의 로직을 실행할 뿐이다.
 *    이렇듯 프로그램의 제어 흐름을 직접 제어하는 것이 아니라 외부에서 관리하는 것을 제어의 역전(IoC)이라 한다.
 *
 *  2.`프레임워크 vs 라이브러리`
 *    프레임워크가 내가 작성한 코드를 제어하고, 대신 실행하면 그것은 프레임워크가 맞다.(JUnit)
 *    반면에 내가 작성한 코드가 직접 제어의 흐름을 담당한다면 그것은 프레임워크가 아니라 라이브러리다.
 *
 *  3.의존관계 주입 DI(Dependency Injection)
 *    `OrderServiceImpl`은 `DiscountPolicy` 인터페이스에 의존한다. 실제 어떤 구현 객체가 사용될지는 모른다.
 *    의존관계는 정적인 클래스 의존 관계와, 실행 시점에 결정되는 동적인 객체(인스턴스) 의존 관계 둘을 분리해서 생각해야 한다.
 *
 *    3_1.정적인 클래스 의존 관계
 *        클래스가 사용하는 import 코드만 보고 의존관계를 쉽게 판단할 수 있다.
 *        정적인 의존관계는 애플리케이션을 실행하지 않아도 분석할 수 있다.
 *
 *    3_2.동적인 객체 인스턴스 의존 관계
 *        애플리케이션 실행 시점에 실제 생성된 객체 인스턴스의 참조가 연결된 의존 관계다.
 *
 *   애플리케이션 실행 시점(런타임)에 외부에서 실제 구현 객체를 생성하고 클라이언트에 전달해서 클라이언트와 서버의 실제 의존관계가 연결 되는 것을 의존관계 주입이라 한다.
 *   객체 인스턴스를 생성하고, 그 참조값을 전달해서 연결된다.
 *   의존관계 주입을 사용하면 클라이언트 코드를 변경하지 않고, 클라이언트가 호출하는 대상의 타입 인스턴스를 변경할 수 있다.
 *   의존관계 주입을 사용하면 정적인 클래스 의존관계를 변경하지 않고, 동적인 객체 인스턴스 의존관계를 쉽게 변경 할 수 있다.
 *
 *
 *
 * -IoC 컨테이너, DI 컨테이너
 *  AppConfig 처럼 객체를 생성하고 관리하면서 의존관계를 연결해 주는 것을 IoC 컨테이너 또는 DI 컨테이너라 한다.
 *  의존관계 주입에 초점을 맞추어 최근에는 주로 `DI 컨테이너`라 한다. 또는 어샘블러, 오브젝트 팩토리 등으로 불리기도 한다.
 *
 *
 *
 * -스프링 컨테이너
 *  ApplicationContext를 스프링 컨테이너라 한다.
 *  기존에는 개발자가 AppConfig를 사용해서 직접 객체를 생성하고 DI를 했지만, 이제부터는 스프링 컨테이너를 통해서 사용한다.
 *  스프링 컨테이너는 @Configuration이 붙은 AppConfig를 설정(구성) 정보로 사용한다. 여기서 @Bean이라 적힌 메서드를 모두 호출해서 반환된 객체를 스프링 컨테이너에 등록한다.
 *  이렇게 스프링 컨테이너에 등록된 객체를 스프링 빈이라 한다.
 *  스프링 빈은 @Bean이 붙은 메서드의 명을 스프링 빈의 이름으로 사용한다.
 *  이전에는 개발자가 필요한 객체를 AppConfig를 사용해서 직접 조회했지만, 이제부터는 스프링 컨테이너를 통 해서 필요한 스프링 빈(객체)를 찾아야 한다.
 *  스프링 빈은 applicationContext.getBean()메서드를 사용해서 찾을 수 있다.
 *      *주의 : `Bean 이름은 항상 다른 이름을 부여`해야 한다. 같은 이름을 부여하면, 다른 Bean이 무시되거나, 기존 Bean을 덮어버리거나 설정에 따라 오류가 발생한다.
 */