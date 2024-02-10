package thespeace.springbasic2.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thespeace.springbasic2.AppConfig;
import thespeace.springbasic2.member.Grade;
import thespeace.springbasic2.member.Member;
import thespeace.springbasic2.member.MemberService;
import thespeace.springbasic2.member.MemberServiceImpl;

public class OrderServiceTest {

    MemberService memberService;
    OrderService orderService;

    @BeforeEach //테스트 실행 전 무조건 실행.
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }

    @Test
    void createOrder() {
        Long memberId = 1L; //null을 담을 수도 있기 때문에 primitive type말고 wrapper type 사용.
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}

/**
 * -단위테스트?
 *  개발자가 코드의 정확성, 신뢰성 및 효율성을 보장할 수 있도록 하는 소프트웨어 개발의 중요한 측면이다.
 *  그 중에서 스프링이나 컨테이너의 도움없이 순수하게 자바코드로 테스트를 하는 것이 중요하다(속도면에서 효율적).
 */