package thespeace.springbasic2.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import thespeace.springbasic2.discount.DiscountPolicy;
import thespeace.springbasic2.discount.FixDiscountPolicy;
import thespeace.springbasic2.discount.RateDiscountPolicy;
import thespeace.springbasic2.member.Member;
import thespeace.springbasic2.member.MemberRepository;
import thespeace.springbasic2.member.MemoryMemberRepository;

@Component
public class OrderServiceImpl implements OrderService{

//    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); //1.고정 할인 금액을-
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();  //2.%할인으로 변경.

    /**
     * -새로운 할인 정책 적용과 문제점
     *  할인 정책을 개발 한 후 적용해본 결과 OrderServiceImpl.java의 코드를 고쳐야 했다.
     *  우리는 `역할과 구현을 충실하게 분리`했고, `다형성을 활용하고, 인터페이스와 구현 객체를 분리`했다.
     *  그러면 우리는 OCP와 DIP같은 객체지향 설계 원칙을 충실히 준수했는가? 그렇게 보이지만 사실은 아니다.
     *  우선 DIP는 주문서비스 클라이언트(OrderServiceImpl.java)는 DiscountPolicy 인터페이스에 의존하면서 DIP를 지킨 것 같지만 추상(인터페이스) 뿐만 아니라 구체(구현) 클래스에도 의존하고 있다.
     *  한마디로 클라이언트(OrderServiceImpl)가 DiscountPolicy 인터페이스 뿐만아니라 구현 클래스(FixDiscountPolicy, RateDiscountPolicy)도 의존하고 있다. 이는 DIP원칙 위반이다.
     *  그리고 클라이언트 코드도 영향을 주었기(변경) 때문에 OCP원칙도 위반하였다.
     *  
     *  
     * -해결 방법
     *  1.DIP 위반 : 구현 클래스가 인터페이스(추상)에만 의존하도록 의존관계 변경.
     *              ```java
     *                  private DiscountPolicy discountPolicy; //인터페이스에만 의존하도록 변경, 하지만 구현체가 없어서 NPE 발생! 이 문제를 해결하려면 누군가가 클라이언트 클래스에 구현객체를 대신 생성하고 주입해주어야 한다.
     *              ```
     *              관심사(책임)의 분리가 필요한 시점이다.
     *              예를 들자면, 애플리케이션(하나의 공연) -> 남자 주인공 배역(인터페이스) -> 배우(구현체), 공연 기획자가 필요한 시점이다.
     *              애플리케이션의 전체 동작 방식을 구성(config)하기 위해, `구현 객체를 생성`하고 `연결`하는 책임을 가지는 별도의 클래스(공연 기획자)를 만들어 해결하자.
     *  2.OCP 위반 : 구성 영역(AppConfig.java)과 사용 영역(Client Codes)을 나누어서 구성 영역만 변경해주면 클라이언트 코드는 변경할 필요가 없어졌다.
     */
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {//[DIP 원칙 완성]생성자를 통해서 구현체를 선택, 추상화에만 의존! `의존관계에 대한 고민은 외부`에 맡기고 `실행에만 집중`하면 된다.
        this.memberRepository = memberRepository;                                              //의존관계를 마치 외부에서 주입해주는 것 같다고해서 DI(Dependency Injection), 의존관계 주입, 의존성 주입이라 한다.
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice); //단일 책임의 원칙이 잘 지켜진 케이스(주문과 할인의 분리)

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //싱글톤 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
