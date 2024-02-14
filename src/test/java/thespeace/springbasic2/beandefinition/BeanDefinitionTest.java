package thespeace.springbasic2.beandefinition;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import thespeace.springbasic2.AppConfig;

public class BeanDefinitionTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 설정 메타정보 확인")
    void findApplicationBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            if(beanDefinition.getRole() == beanDefinition.ROLE_APPLICATION) {
                System.out.println("beanDefinitionName = " + beanDefinitionName +
                        ", beanDefinition = " + beanDefinition);
            }
        }

    }
}
/**
 *  -스프링 빈 설정 메타 정보 - BeanDefinition
 *   스프링은 어떻게 이런 다양한 설정 형식을 지원하는 것일까? 그 중심에는 BeanDefinition이라는 추상화가 있다.
 *   쉽게 이야기해서 역할과 구현을 개념적으로 나눈 것이다!
 *      XML을 읽어서 BeanDefinition을 만들면 된다.
 *      자바 코드를 읽어서 BeanDefinition을 만들면 된다.
 *      스프링 컨테이너는 자바 코드인지, XML인지 몰라도 된다. 오직 BeanDefinition만 알면 된다.
 *   BeanDefinition을 빈 설정 메타정보라 한다.
 *      @Bean , <bean>당 각각 하나씩 메타 정보가 생성된다.
 *   스프링 컨테이너는 이 메타정보를 기반으로 스프링 빈을 생성한다.
 *   AnnotationConfigApplicationContext는 AnnotatedBeanDefinitionReader를 사용해서 AppConfig.class를 읽고 BeanDefinition을 생성한다.
 *   GenericXmlApplicationContext는 XmlBeanDefinitionReader를 사용해서 appConfig.xml 설정 정보를 읽고 BeanDefinition을 생성한다.
 *   새로운 형식의 설정 정보가 추가되면, XxxBeanDefinitionReader를 만들어서 BeanDefinition을 생성하 면 된다.
 *
 *
 *
 *  -BeanDefinition 살펴보기
 *   BeanClassName: 생성할 빈의 클래스 명(자바 설정 처럼 팩토리 역할의 빈을 사용하면 없음)
 *   factoryBeanName: 팩토리 역할의 빈을 사용할 경우 이름, 예) appConfig
 *   factoryMethodName: 빈을 생성할 팩토리 메서드 지정, 예) memberService
 *   Scope: 싱글톤(기본값)
 *   lazyInit: 스프링 컨테이너를 생성할 때 빈을 생성하는 것이 아니라, 실제 빈을 사용할 때 까지 최대한 생성을 지연 처리 하는지 여부
 *   InitMethodName: 빈을 생성하고, 의존관계를 적용한 뒤에 호출되는 초기화 메서드 명
 *   DestroyMethodName: 빈의 생명주기가 끝나서 제거하기 직전에 호출되는 메서드 명
 *   Constructor arguments, Properties: 의존관계 주입에서 사용한다. (자바 설정 처럼 팩토리 역할의 빈을 사용 하면 없음)
 *
 *
 *
 *  -정리
 *   BeanDefinition을 직접 생성해서 스프링 컨테이너에 등록할 수 도 있다.
 *   하지만 실무에서 BeanDefinition을 직접 정의하거나 사용할 일은 거의 없다.
 *   BeanDefinition에 대해서는 너무 깊이있게 이해하기 보다는, 스프링이 다양한 형태의 설정 정보를 BeanDefinition으로 추상화해서 사용하는 것 정도만 이해하면 된다.
 */