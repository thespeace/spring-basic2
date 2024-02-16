package thespeace.springbasic2;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(//설정 정보가 없어도 자동으로 스프링 빈을 등록하는 기능.
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class) //기존 예제 코드를 유지하기 위해 excludeFilters를 이용해서 설정정보는 컴포넌트 스캔 대상에서 제외.
)
public class AutoAppConfig {


}
