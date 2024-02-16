package thespeace.springbasic2.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {//구현체가 하나만 존재할때는 파일명뒤에 impl을 붙이는 관례가 있다.

    private final MemberRepository memberRepository;

    @Autowired //자동 의존 관계 주입(스프링이 해당 메서드의 파라미터 타입과 맞는 bean을 찾아서 자동으로 연결 후 주입해준다.), == ac.getBean(MemberRepository.class)
    public MemberServiceImpl(MemberRepository memberRepository) {//[DIP 원칙 완성]생성자를 통해서 구현체를 선택, 추상화에만 의존! `의존관계에 대한 고민은 외부`에 맡기고 `실행에만 집중`하면 된다.
        this.memberRepository = memberRepository;                //의존관계를 마치 외부에서 주입해주는 것 같다고해서 DI(Dependency Injection), 의존관계 주입, 의존성 주입이라 한다.
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }


    //싱글톤 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
