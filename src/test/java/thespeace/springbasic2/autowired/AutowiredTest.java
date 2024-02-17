package thespeace.springbasic2.autowired;

import io.micrometer.common.lang.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import thespeace.springbasic2.member.Member;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void AutowiredOption() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    static class TestBean {

        //호출 안됨
        @Autowired(required = false)
        public void setNoBean1(Member noBean1) {
            System.out.println("noBean1 = " + noBean1);
        }

        //null 호출
        @Autowired
        public void setNoBean2(@Nullable Member noBean2) {
            System.out.println("noBean2 = " + noBean2);
        }

        //Optional.empty 호출
        @Autowired
        public void setNoBean3(Optional<Member> noBean3) {
            System.out.println("noBean3 = " + noBean3);
        }
    }
}

/**
 *  -옵션 처리
 *   주입할 스프링 빈이 없어도 동작해야 할 때가 있다.
 *   그런데 @Autowired만 사용하면 required 옵션의 기본값이 true로 되어 있어서 자동 주입 대상이 없으면 오류가 발생한다.
 *   자동 주입 대상을 옵션으로 처리하는 방법은 다음과 같다.
 *
 *      @Autowired(required=false) : 자동 주입할 대상이 없으면 수정자 메서드 자체가 호출 안됨
 *      org.springframework.lang.@Nullable : 자동 주입할 대상이 없으면 null이 입력된다.
 *      Optional<> : 자동 주입할 대상이 없으면 Optional.empty가 입력된다.
 *
 *   참고: @Nullable, Optional은 스프링 전반에 걸쳐서 지원된다. 예를 들어서 생성자 자동 주입에서 특정 필드에 만 사용해도 된다.
 */