package thespeace.springbasic2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
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
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();//AppConfig에서 인터페이스에 어떤 구현체를 할당해야 할지를 정해서 객체 생성을 한다. 구체 클래스를 의존할 필요가 없어짐.

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class); //AppConfig의 환경 설정 정보를 Spring이 스프링 컨테이너에 객체(@Bean)들을 생성하여 관리해준다.
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);//객체는 기본적으로 메서드 이름으로 등록된다. 인자 1은 객체 2는 반환타입.

        Member member = new Member(1L, "memberA", Grade.BASIC);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("find member = " + findMember.getName());
    }
}
