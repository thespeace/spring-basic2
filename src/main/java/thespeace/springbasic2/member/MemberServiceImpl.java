package thespeace.springbasic2.member;

public class MemberServiceImpl implements MemberService {//구현체가 하나만 존재할때는 파일명뒤에 impl을 붙이는 관례가 있다.

    private final MemberRepository memberRepository;

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

}
