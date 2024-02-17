package thespeace.springbasic2;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LombokTest {

    private String name;
    private int age;

    public static void main(String[] args) {
        LombokTest lombok = new LombokTest();
        lombok.setName("lombokTest");

        String name = lombok.getName();
        System.out.println("name = " + name);

        System.out.println("lombok = " + lombok);
    }
}

/**
 *  -롬복과 최신 트랜드
 *   막상 개발을 해보면, 대부분이 다 불변이고, 그래서 다음과 같이 필드에 final 키워드를 사용하게 된다.
 *   그런데 생성자도 만들어야 하고, 주입 받은 값을 대입하는 코드도 만들어야 하고 작성할 코드가 많은데, 이를 편리하게 만들어주는 라이브러리가 바로 Lombok이다.
 *
 *   롬복이 자바의 애노테이션 프로세서라는 기능을 이용해서 컴파일 시점에 작성한 애노테이션을 확인 후 코드를 자동으로 생성해준다.
 *   에시로 OrderServiceImpl.java의 @RequiredArgsConstructor 기능을 살펴보자.
 *
 *   정리
 *      최근에는 생성자를 딱 1개 두고, @Autowired를 생략하는 방법을 주로 사용한다. 여기에 Lombok 라이브러리의 @RequiredArgsConstructor함께 사용하면 기능은 다 제공하면서, 코드는 깔끔하게 사용할 수 있다.
 */