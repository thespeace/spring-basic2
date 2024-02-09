package thespeace.springbasic2.member;

public class MemberServiceImpl implements MemberService {//구현체가 하나만 존재할때는 파일명뒤에 impl을 붙이는 관례가 있다.

    private final MemberRepository memberRepository = new MemoryMemberRepository();

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
