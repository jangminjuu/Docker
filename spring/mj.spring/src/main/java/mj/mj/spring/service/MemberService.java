package mj.mj.spring.service;

import mj.mj.spring.domain.Member;
import mj.mj.spring.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Transactional
public class MemberService {



    /* 회원 서비스 생성 */


    private final MemberRepository memberRepository;
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
// 회원 가입

    public Long join(Member member) {

        long start = System.currentTimeMillis();
        // 같은 이름의 중복 회원 안됨
        // option + command + v = 바로 리턴 값 설정 가능 (optional)
        /* optional 안에 멤버 객체가 있음 (옵셔널을 통한 여러 메소드를 쓸 수가 있음)
        // 이미 존재하는 회원 인 경우 예외처리, 그렇지 않은 경우에 회원 저장
         */
        // control + T 로 메소드 Extract (ctrl + T = 여러 Refactoring 나옴)
        // Extract Method 는 단축키로는 command + Option M

        // 같은 이름의 중복 회원 X
        try {
            validateDuplicateMember(member);// 중복 회원 검증
            memberRepository.save(member);
            return member.getId();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("join = " + timeMs + "ms");
        }
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    // 전체 회원 조회
    public List<Member> findMembers() {
        long start = System.currentTimeMillis();
        try {

        return memberRepository.findAll();
    } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("findMembers = " + timeMs + "ms");
        }

        }




    // findbyid 했을 때 id 넘겨서 반환
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
