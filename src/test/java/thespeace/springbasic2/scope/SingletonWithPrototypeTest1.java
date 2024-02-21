package thespeace.springbasic2.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {

    //스프링 컨테이너에 프로토타입 빈 직접 요청.
    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class); //프로토타입 빈(x01)
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class); //프로토타입 빈(x02)
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    //싱글톤 빈이 의존관계 주입을 통해서 프로토타입 빈을 주입받아서 사용.
    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        ClientBean clientBean1 = ac.getBean(ClientBean.class); //싱글톤 빈(x01)
        int count1 = clientBean1.logic(); // 프로토타입의 addCount()를 호출, 프로토타입 빈의 count를 증가.
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class); //싱글톤 빈(x01)
        int count2 = clientBean2.logic(); // 프로토타입의 addCount()를 호출, 프로토타입 빈의 count를 증가.
        assertThat(count2).isEqualTo(1);
        //여기서 중요한 점이 있는데, clientBean이 내부에 가지고 있는 프로토타입 빈은 이미 과거에 주입이 끝난 빈이다.
        //주입 시점에 스프링 컨테이너에 요청해서 프로토타입 빈이 새로 생성이 된 것이지, 사용 할 때마다 새로 생성되는 것이 아니다!
    }

    /**
     *  -싱글톤 빈이 의존관계 주입을 통해서 프로토타입 빈을 주입받아서 사용.
     *   clientBean 은 싱글톤이므로, 보통 스프링 컨테이너 생성 시점에 함께 생성되고, 의존관계 주입도 발생한다.
     *      1. clientBean 은 의존관계 자동 주입을 사용한다. 주입 시점에 스프링 컨테이너에 프로토타입 빈을 요청한다.
     *      2. 스프링 컨테이너는 프로토타입 빈을 생성해서 clientBean 에 반환한다. 프로토타입 빈의 count 필드 값은 0이다.
     *   이제 clientBean 은 프로토타입 빈을 내부 필드에 보관한다. (정확히는 참조값을 보관한다.)
     */
    @Scope("singleton")
    static class ClientBean {

        @Autowired
        private ObjectProvider<PrototypeBean> prototypeBeanProvider;

        public int logic() {
            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}

/**
 *  -프로토타입 스코프 - 싱글톤 빈과 함께 사용시 문제점
 *   스프링은 일반적으로 싱글톤 빈을 사용하므로, 싱글톤 빈이 프로토타입 빈을 사용하게 된다.
 *   그런데 싱글톤 빈은 생성 시점에만 의존관계 주입을 받기 때문에, 프로토타입 빈이 새로 생성되기는 하지만, 싱글톤 빈과 함께 계속 유지되는 것이 문제다.
 *   아마 원하는 것이 이런 것은 아닐 것이다. 프로토타입 빈을 주입 시점에만 새로 생성하는게 아니라, 사용할 때 마다 새로 생성해서 사용하는 것을 원할 것이다.
 *
 *   참고: 여러 빈에서 같은 프로토타입 빈을 주입 받으면, 주입 받는 시점에 각각 새로운 프로토타입 빈이 생성된다.
 *         예를 들어서 clientA, clientB가 각각 의존관계 주입을 받으면 각각 다른 인스턴스의 프로토타입 빈을 주입 받는다.
 *              clientA prototypeBean@x01
 *              clientB prototypeBean@x02
 *         물론 사용할 때 마다 새로 생성되는 것은 아니다.
 *
 *
 *
 *  -프로토타입 스코프 - 싱글톤 빈과 함께 사용시 Provider로 문제 해결
 *   싱글톤 빈과 프로토타입 빈을 함께 사용할 때, 어떻게 하면 사용할 때 마다 항상 새로운 프로토타입 빈을 생성할 수 있을까?
 *
 *   1.스프링 컨테이너에 요청
 *     가장 간단한 방법은 싱글톤 빈이 프로토타입을 사용할 때 마다 스프링 컨테이너에 새로 요청하는 것이다.
 *      {@code
 *      static class ClientBean {
 *
 *          @Autowired
 *          private ApplicationContext ac;
 *
 *          public int logic() {
 *              PrototypeBean prototypeBean = ac.getBean(PrototypeBean.class);
 *              prototypeBean.addCount();
 *              int count = prototypeBean.getCount();
 *              return count;
 *          }
 *      }
 *      }
 *      실행해보면 ac.getBean() 을 통해서 항상 새로운 프로토타입 빈이 생성되는 것을 확인할 수 있다.
 *      의존관계를 외부에서 주입(DI) 받는게 아니라 이렇게 직접 필요한 의존관계를 찾는 것을 Dependency Lookup(DL) 의존관계 조회(탐색) 이라한다.
 *      그런데 이렇게 스프링의 애플리케이션 컨텍스트 전체를 주입받게 되면, 스프링 컨테이너에 종속적인 코드가 되고, 단위 테스트도 어려워진다.
 *      지금 필요한 기능은 지정한 프로토타입 빈을 컨테이너에서 대신 찾아주는 딱! DL 정도의 기능만 제공하는 무언가가 있으면 된다.
 *
 *   스프링에는 이미 모든게 준비되어 있다.
 *
 *   2.ObjectFactory, ObjectProvider
 *     지정한 빈을 컨테이너에서 대신 찾아주는 DL 서비스를 제공하는 것이 바로 ObjectProvider 이다. 참고로 과거에는 ObjectFactory 가 있었는데, 여기에 편의 기능을 추가해서 ObjectProvider 가 만들어졌다.
 *      {@code
 *      static class ClientBean {
 *
 *          @Autowired
 *          private ObjectProvider<PrototypeBean> prototypeBeanProvider;
 *
 *          public int logic() {
 *              PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
 *              prototypeBean.addCount();
 *              int count = prototypeBean.getCount();
 *              return count;
 *          }
 *      }
 *      }
 *      실행해보면 prototypeBeanProvider.getObject() 을 통해서 항상 새로운 프로토타입 빈이 생성되는 것을 확인할 수 있다.
 *      ObjectProvider 의 getObject() 를 호출하면 내부에서는 스프링 컨테이너를 통해 해당 빈을 찾아서 반환한다. (DL)
 *      스프링이 제공하는 기능을 사용하지만, 기능이 단순하므로 단위테스트를 만들거나 mock 코드를 만들기는 훨씬 쉬워진다.
 *      ObjectProvider 는 지금 딱 필요한 DL 정도의 기능만 제공한다
 *
 *      특징
 *          ObjectFactory: 기능이 단순, 별도의 라이브러리 필요 없음, 스프링에 의존.
 *          ObjectProvider: ObjectFactory 상속, 옵션, 스트림 처리등 편의 기능이 많고, 별도의 라이브러리 필요 없음, 스프링에 의존.
 */