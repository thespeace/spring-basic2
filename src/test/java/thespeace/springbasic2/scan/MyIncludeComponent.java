package thespeace.springbasic2.scan;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeComponent {//컴포넌트 스캔 대상에 추가할 애노테이션.
}
