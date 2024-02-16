package thespeace.springbasic2.scan;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcludeComponent {//컴포넌트 스캔 대상에서 제외할 애노테이션.
}
