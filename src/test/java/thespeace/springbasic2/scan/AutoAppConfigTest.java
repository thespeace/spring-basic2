package thespeace.springbasic2.scan;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import thespeace.springbasic2.AutoAppConfig;
import thespeace.springbasic2.member.MemberRepository;
import thespeace.springbasic2.member.MemberService;
import thespeace.springbasic2.order.OrderServiceImpl;

import static org.assertj.core.api.Assertions.*;

public class AutoAppConfigTest {

    @Test
    void basicScan(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        MemberService memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberService.class);

        OrderServiceImpl bean = ac.getBean(OrderServiceImpl.class);
        MemberRepository memberRepository = bean.getMemberRepository();
        System.out.println("memberRepository = " + memberRepository);
    }
}

/**
 *  -컴포넌트 스캔과 의존관계 자동 주입 시작하기
 *   지금까지 스프링 빈을 등록할 때는 자바 코드의 @Bean이나 XML의 <bean> 등을 통해서 설정 정보에 직접 등 록할 스프링 빈을 나열했다.
 *   예제에서는 몇개가 안되었지만, 이렇게 등록해야 할 스프링 빈이 수십, 수백개가 되면 일일이 등록하기도 귀찮고, 설정 정보도 커지고, 누락하는 문제도 발생한다.
 *   그래서 스프링은 설정 정보가 없어도 자동으로 스프링 빈을 등록하는 컴포넌트 스캔이라는 기능을 제공한다. 또 의존관계도 자동으로 주입하는 @Autowired라는 기능도 제공한다.
 *
 *   `AutoAppConfig.java` 코드 확인.
 *   @ComponentScan은 이름 그대로 @Component 애노테이션이 붙은 클래스를 스캔해서 스프링 빈으로 등록한다.
 *
 *   그러면 의존관계 주입은?
 *   이전에 AppConfig에서는 @Bean으로 직접 설정 정보를 작성했고, 의존관계도 직접 명시했다. 이제는 이런 설정 정보 자체가 없기 때문에, 의존관계 주입도 이 클래스 안에서 해결해야 한다.
 *   @Autowired는 의존관계를 자동으로 주입해준다.
 *   @Autowired를 사용하면 생성자에서 여러 의존관계도 한번에 주입받을 수 있다.
 *
 *   로그를 통해 ClassPathBeanDefinitionScanner를 찾아 컴포넌트 스캔이 잘 동작하는 것을 확인 할 수 있다.
 *
 *
 *
 *  -컴포넌트 스캔과 자동 의존관계 주입의 동작
 *   1.@ComponentScan
 *      @ComponentScan은 @Component가 붙은 모든 클래스를 스프링 빈으로 등록한다.
 *      이때 스프링 빈의 기본 이름은 클래스명을 사용하되 맨 앞글자만 소문자를 사용한다.
 *          빈 이름 기본 전략: MemberServiceImpl 클래스 -> memberServiceImpl
 *          빈 이름 직접 지정: 만약 스프링 빈의 이름을 직접 지정하고 싶으면 @Component("memberService2") 이런식으로 이름을 부여하면 된다
 *
 *   2.@Autowired 의존관계 자동 주입
 *      생성자에 @Autowired를 지정하면, 스프링 컨테이너가 자동으로 해당 스프링 빈을 찾아서 주입한다.
 *      이때 기본 조회 전략은 타입이 같은 빈을 찾아서 주입한다.
 *          getBean(MemberRepository.class)와 동일하다고 이해하면 된다.
 *          생성자에 파라미터가 많아도 다 찾아서 자동으로 주입한다.
 */