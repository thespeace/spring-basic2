package thespeace.springbasic2.lifecycle;

//외부 네트워크에 미리 연결하는 클래스.
public class NetworkClient{

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
    }

    public void setUrl(String url) {//객체를 생성한 다음에 외부에서 수정자 주입을 통해서 해당 메서드가 호출되어야 url이 존재.
        this.url = url;
    }

    //서비스 시작시 호출
    public void connect() {
        System.out.println("connect : " + url);
    }

    public void call(String message) {
        System.out.println("call : " + url + " message = " + message);
    }

    //서비스 종료시 호출
    public void disConnect() {
        System.out.println("close : " + url);
    }

    public void init() {
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지");
    }

    public void close() {
        System.out.println("NetworkClient.close");
        disConnect();
    }
}

/**
 *  -인터페이스 InitializingBean, DisposableBean
 *   코드 실행 후 출력 결과를 보면 초기화 메서드가 주입 완료 후에 적절하게 호출 된 것을 확인할 수 있다.
 *   그리고 스프링 컨테이너의 종료가 호출되자 소멸 메서드가 호출 된 것도 확인할 수 있다
 *
 *   초기화, 소멸 인터페이스 단점
 *      1. 이 인터페이스는 스프링 전용 인터페이스다. 해당 코드가 스프링 전용 인터페이스에 의존한다.
 *      2. 초기화, 소멸 메서드의 이름을 변경할 수 없다.
 *      3. 내가 코드를 고칠 수 없는 외부 라이브러리에 적용할 수 없다
 *
 *   참고: 인터페이스를 사용하는 초기화, 종료 방법은 스프링 초창기에 나온 방법들이고, 지금은 다음의 더 나은 방법들이 있어서 거의 사용하지 않는다.
 *
 *
 *
 *  -빈 등록 초기화, 소멸 메서드 지정
 *   설정 정보에 @Bean(initMethod = "init", destroyMethod = "close") 처럼 초기화, 소멸 메서드를 지정할 수 있다.
 *
 *   설정 정보 사용 특징
 *      메서드 이름을 자유롭게 줄 수 있다.
 *      스프링 빈이 스프링 코드에 의존하지 않는다.
 *      코드가 아니라 설정 정보를 사용하기 때문에 코드를 고칠 수 없는 외부 라이브러리에도 초기화, 종료 메서드를 적용할 수 있다.
 *
 *   종료 메서드 추론
 *      @Bean의 destroyMethod 속성에는 아주 특별한 기능이 있다.
 *      라이브러리는 대부분 close , shutdown 이라는 이름의 종료 메서드를 사용한다.
 *      @Bean의 destroyMethod 는 기본값이 (inferred) (추론)으로 등록되어 있다.
 *      이 추론 기능은 close , shutdown 라는 이름의 메서드를 자동으로 호출해준다. 이름 그대로 종료 메서드를 추론해서 호출해준다.
 *      따라서 직접 스프링 빈으로 등록하면 종료 메서드는 따로 적어주지 않아도 잘 동작한다.
 *      추론 기능을 사용하기 싫으면 destroyMethod="" 처럼 빈 공백을 지정하면 된다.
 */