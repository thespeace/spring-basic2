package thespeace.springbasic2;

import thespeace.springbasic2.member.Grade;
import thespeace.springbasic2.member.Member;
import thespeace.springbasic2.member.MemberService;
import thespeace.springbasic2.member.MemberServiceImpl;

/*
*   -회원 도메인 요구사항
*
*/
public class MemberApp {
    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl();
        Member member = new Member(1L, "memberA", Grade.BASIC);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("find member = " + findMember.getName());
    }
}
