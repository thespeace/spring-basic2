package thespeace.springbasic2.beanfind;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import thespeace.springbasic2.AppConfig;


/**
 * -컨테이너에 등록된 모든 빈 조회
 *  실무에서 Bean을 조회할 일은 거의 없다.
 *  하지만 기본 기능이기도 하고 가끔 순수한 Java 애플리케이션에서 스프링 컨테이너를 생성해서 써야하는 일이 있기 때문에 알고 넘어가자.
 */
public class ApplicationContextInfoTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("모든 빈 출력하기")
    void findAllBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();//getBeanDefinitionNames() : 스프링에 등록된 모든 빈 이름을 조회한다.
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = ac.getBean(beanDefinitionName);//getBean() : 빈 이름으로 빈 객체(인스턴스)를 조회한다.
            System.out.println("bean = " + beanDefinitionName + " object = " + bean);
        }
    }

    @Test
    @DisplayName("애플리케이션 빈 출력하기")
    void findApplicationAllBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);//Bean의 메타데이터 정보들.

            //Role ROLE_APPLICATION: 직접 등록한 애플리케이션 Bean
            // Role ROLE_INFRASTRUCTURE: 스프링이 내부에서 사용하는 Bean
            if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) { //스프링이 내부에서 사용하는 빈은 `getRole()`로 구분할 수 있다.
                Object bean = ac.getBean(beanDefinitionName);
                System.out.println("bean = " + beanDefinitionName + " object = " + bean);
            }
        }
    }
}
