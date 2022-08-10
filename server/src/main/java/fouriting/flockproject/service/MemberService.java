package fouriting.flockproject.service;

import fouriting.flockproject.domain.Member;
import fouriting.flockproject.domain.dto.MemberLogInDto;
import fouriting.flockproject.domain.dto.MemberResponseDto;
import fouriting.flockproject.domain.dto.MemberSignUpDto;
import fouriting.flockproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponseDto signUp(MemberSignUpDto memberSignUpDto){
        if(memberRepository.findById(memberSignUpDto.getLoginId()) != null)
            throw new IllegalArgumentException("이미 가입되어 있는 유저입니다.");

        Member member = memberSignUpDto.sendMember();
        memberRepository.save(member);
        MemberResponseDto memberResponseDto = new MemberResponseDto().memberResponse(member);

        return memberResponseDto;
    }

    @Transactional
    public MemberResponseDto logIn(MemberLogInDto memberLogInDto){
        Member findedMember = memberRepository.findById(memberLogInDto.getLoginId());
        MemberResponseDto memberResponseDto = new MemberResponseDto().memberResponse(findedMember);

        return memberResponseDto;
    }
}
