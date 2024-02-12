package thespeace.springbasic2;

import thespeace.springbasic2.discount.DiscountPolicy;
import thespeace.springbasic2.discount.FixDiscountPolicy;
import thespeace.springbasic2.discount.RateDiscountPolicy;
import thespeace.springbasic2.member.MemberRepository;
import thespeace.springbasic2.member.MemberService;
import thespeace.springbasic2.member.MemberServiceImpl;
import thespeace.springbasic2.member.MemoryMemberRepository;
import thespeace.springbasic2.order.OrderService;
import thespeace.springbasic2.order.OrderServiceImpl;

/**
 * -AppConfig
 *  애플리케이션의 실제 동작에 필요한 `구현 객체를 생성`한다.
 *  생성한 객체 인스턴스의 참조(래퍼런스)를 `생성자를 통해 주입(연결)`해준다.
 *  즉 객체의 생성과 연결은 `AppConfig`가 담당한다.
 *  이를 통해 `DIP 원칙이 완성`되었고, `관심사의 분리`를 이뤘다.
 *  AppConfig에는 `중복`은 없애고, `역할`에 따른 `구현`이 잘 보여야 한다(설계와 구성 정보가 그대로 드러나게).
 */
public class AppConfig {

    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository()); //생성자 주입.
    }

    private MemberRepository memberRepository() {
        return new MemoryMemberRepository(); //중복 제거, 다른 구현체로 변경할 때 한 부분만 변경하면 된다.
    }

    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    public DiscountPolicy discountPolicy() {
        return new FixDiscountPolicy();
    }
}
