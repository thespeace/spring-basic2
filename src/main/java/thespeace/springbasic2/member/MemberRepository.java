package thespeace.springbasic2.member;

public interface MemberRepository {

    void save(Member member);

    Member findById(Long memberId);
}
