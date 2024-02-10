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
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();//AppConfig에서 인터페이스에 어떤 구현체를 할당해야 할지를 정해서 객체 생성을 한다. 구체 클래스를 의존할 필요가 없어짐.
        Member member = new Member(1L, "memberA", Grade.BASIC);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("find member = " + findMember.getName());
    }
}
