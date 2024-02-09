package thespeace.springbasic2.member;

import java.util.HashMap;
import java.util.Map;

//데이터베이스가 확정되지 않아서 메모리에 저장하기 위해 만든 클래스(테스트용).
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
