package thespeace.springbasic2.lifecycle;

//외부 네트워크에 미리 연결하는 클래스.
public class NetworkClient {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
        connect();
        call("초기화 연결 메시지");
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
    public void disconnect() {
        System.out.println("close : " + url);
    }
}
