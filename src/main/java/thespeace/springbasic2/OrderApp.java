package thespeace.springbasic2;

import thespeace.springbasic2.member.Grade;
import thespeace.springbasic2.member.Member;
import thespeace.springbasic2.member.MemberService;
import thespeace.springbasic2.member.MemberServiceImpl;
import thespeace.springbasic2.order.Order;
import thespeace.springbasic2.order.OrderService;
import thespeace.springbasic2.order.OrderServiceImpl;

public class OrderApp {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig(); //AppConfig에서 인터페이스에 어떤 구현체를 할당해야 할지를 정해서 객체 생성을 한다. 구체 클래스를 의존할 필요가 없어짐.
        MemberService memberService = appConfig.memberService();
        OrderService orderService = appConfig.orderService();

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);

        System.out.println("order = " + order.toString());
        System.out.println("order.calculatePrice() = " + order.calculatePrice());
    }
}
