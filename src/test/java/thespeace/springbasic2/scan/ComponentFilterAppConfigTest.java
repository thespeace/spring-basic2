package thespeace.springbasic2.scan;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.context.annotation.ComponentScan.*;

public class ComponentFilterAppConfigTest {

    @Test
    void filterScan() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);

        //includeFilters에 MyIncludeComponent 애노테이션을 추가해서 BeanA가 스프링 빈에 등록된다.
        BeanA beanA = ac.getBean("beanA", BeanA.class);
        assertThat(beanA).isNotNull();

        //excludeFilters에 MyExcludeComponent 애노테이션을 추가해서 BeanB는 스프링 빈에 등록되지 않는다.
        assertThrows(
                NoSuchBeanDefinitionException.class,
                () -> ac.getBean("beanB", BeanB.class));
    }

    @Configuration
    @ComponentScan(
            includeFilters = @Filter(type = FilterType.ANNOTATION,  classes = MyIncludeComponent.class), //includeFilters : 컴포넌트 스캔 대상을 추가로 지정한다.
            excludeFilters = @Filter(type = FilterType.ANNOTATION,  classes = MyExcludeComponent.class)  //excludeFilters : 컴포넌트 스캔에서 제외할 대상을 지정한다.
    )
    static class ComponentFilterAppConfig {

    }
}

/**
 *  -필터(@Filter)
 *   컴포넌트 스캔 대상에 추가할지 제외할지를 @Filter로 지정할 수 있다.
 *
 *   FilterType 옵션
 *      FilterType은 5가지 옵션이 있다.
 *          1.ANNOTATION: 기본값, 애노테이션을 인식해서 동작한다. ex) org.example.SomeAnnotation
 *          2.ASSIGNABLE_TYPE: 지정한 타입과 자식 타입을 인식해서 동작한다. ex) org.example.SomeClass
 *          3.ASPECTJ: AspectJ 패턴 사용. ex) org.example..*Service+
 *          4.REGEX: 정규 표현식. ex) org\.example\.Default.*
 *          5.CUSTOM: TypeFilter이라는 인터페이스를 구현해서 처리. ex) org.example.MyTypeFilter
 *
 *   참고: @Component면 충분하기 때문에, includeFilters를 사용할 일은 거의 없다. excludeFilters는 여러가지 이유로 간혹 사용할 때가 있지만 많지는 않다.
 *        특히 최근 스프링 부트는 컴포넌트 스캔을 기본으로 제공하는데, 개인적으로는 옵션을 변경하면서 사용하기 보다 는 스프링의 기본 설정에 최대한 맞추어 사용하는 것을 권장하고, 선호하는 편이다.
 */