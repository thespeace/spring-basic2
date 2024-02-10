package thespeace.springbasic2;

import thespeace.springbasic2.discount.RateDiscountPolicy;
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
 */
public class AppConfig {

    public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository()); //생성자 주입.
    }

    public OrderService orderService() {
        return new OrderServiceImpl(new MemoryMemberRepository(), new RateDiscountPolicy());
    }
}
