package thespeace.springbasic2.order;

import thespeace.springbasic2.discount.DiscountPolicy;
import thespeace.springbasic2.discount.FixDiscountPolicy;
import thespeace.springbasic2.member.Member;
import thespeace.springbasic2.member.MemberRepository;
import thespeace.springbasic2.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{

    //구현체
    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice); //단일 책임의 원칙이 잘 지켜진 케이스(주문과 할인의 분리)

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
