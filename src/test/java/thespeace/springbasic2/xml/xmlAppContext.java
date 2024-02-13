package thespeace.springbasic2.xml;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import thespeace.springbasic2.member.MemberService;

/**
 *  -다양한 설정 형식 지원 - 자바 코드, XML
 *   스프링 컨테이너는 다양한 형식의 설정 정보를 받아들일 수 있게 유연하게 설계되어 있다.(자바 코드, XML, Groovy 등등)
 *
 *   1.애노테이션 기반 자바 코드 설정 사용
 *     현재 예제 프로젝트 설정이다.
 *     `new AnnotationConfigApplicationContext(AppConfig.class)`,`AnnotationConfigApplicationContext.class`를 사용하면서 자바 코드로된 설정 정보를 넘기면 된다
 *
 *   2.XML 설정 사용
 *     최근에는 스프링 부트를 많이 사용하면서 XML기반의 설정은 잘 사용하지 않는다.
 *     아직 많은 레거시 프로젝트 들 이 XML로 되어 있고, 또 XML을 사용하면 컴파일 없이 빈 설정 정보를 변경할 수 있는 장점도 있으므로 한번쯤 배워두는 것도 괜찮다.
 *     GenericXmlApplicationContext를 사용하면서 xml설정 파일을 넘기면 된다.
 *
 *
 *
 *  -xml 기반의 스프링 빈 설정 정보 **
 *   xml 기반의 `appConfig.xml`은 스프링 설정 정보와 자바 코드로 된 `AppConfig.java`의 설정 정보와 비교해보면 거의 비슷하다는 것을 알 수 있다.
 *   xml 기반으로 설정하는 것은 최근에 잘 사용하지 않으므로 이정도로 마무리 하고, 필요하면 스프링 공식 레퍼런스 문서를 확인하자. [https://spring.io/projects/spring-framework]
 */
public class xmlAppContext {

    @Test
    void xmlAppContext() {
        ApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
    }
}
