package thespeace.springbasic2.web;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import thespeace.springbasic2.common.MyLogger;

/**
 *  -로거가 잘 작동하는지 확인하는 테스트용 컨트롤러
 *   여기서 HttpServletRequest를 통해서 요청 URL을 받았다.
 *      requestURL 값 http://localhost:8080/log-demo
 *   이렇게 받은 requestURL 값을 myLogger에 저장해둔다. myLogger는 HTTP 요청 당 각각 구분되므로 다른 HTTP 요청 때문에 값이 섞이는 걱정은 하지 않아도 된다.
 *   컨트롤러에서 controller test라는 로그를 남긴다.
 *
 *   참고: requestURL을 MyLogger에 저장하는 부분은 컨트롤러 보다는 공통 처리가 가능한 `스프링 인터셉터`나 `서블릿 필터` 같은 곳을 활용하는 것이 좋다.
 *        여기서는 예제를 단순화하기 위해 컨트롤러를 사용했다.
 */
@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;

    /**
     *  -스코프와 Provider
     *   스프링 애플리케이션을 실행하는 시점에 싱글톤 빈은 생성해서 주입이 가능하지만, request 스코프 빈은 아직 생성되지 않는다. 이 빈은 실제 고객의 요청이 와야 생성할 수 있다!
     *
     *   해결 방법
     *      1.Provider
     *
     */
    private final ObjectProvider<MyLogger> myLoggerProvider; //ObjectProvider로 MyLogger를 디펜던시 룩업할 수 있게 되었다.

    @RequestMapping("log-demo")
    @ResponseBody //문자열 바로 반환.
    public String logDemo(HttpServletRequest request) throws InterruptedException {
        MyLogger myLogger = myLoggerProvider.getObject(); //ObjectProvider 덕분에 ObjectProvider.getObject() 를 호출하는 시점까지 request scope 빈의 생성을 지연할 수 있다.
                                                          //또한 ObjectProvider.getObject() 를 호출하시는 시점에는 HTTP 요청이 진행중이므로 request scope 빈 생성이 정상 처리된다.
                                                          //또한 LogDemoController , LogDemoService 에서 각각 한번씩 따로 호출해도 같은 HTTP 요청이면 같은 스프링 빈이 반환된다!
        String requestURL = request.getRequestURI().toString();
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        Thread.sleep(1000); //로그 확인용, http 요청이 많아서 섞이더라도 각 요청마다 각각의 빈 인스턴스가 관리되는 것을 확인 하기 위해 추가한 코드.
        logDemoService.logic("testId");

        return "OK";
    }
}
