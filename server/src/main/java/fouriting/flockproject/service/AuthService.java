package fouriting.flockproject.service;

import fouriting.flockproject.config.SessionConstant;
import fouriting.flockproject.domain.Member;
import fouriting.flockproject.domain.dto.request.auth.MemberLogInDto;
import fouriting.flockproject.domain.dto.response.MemberResponseDto;
import fouriting.flockproject.domain.dto.request.auth.MemberSignUpDto;
import fouriting.flockproject.exception.ErrorCode;
import fouriting.flockproject.exception.custom.IdDuplicateException;
import fouriting.flockproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberResponseDto signUp(MemberSignUpDto memberSignUpDto){
        if(memberRepository.findByLoginId(memberSignUpDto.getLoginId()).isPresent())
            throw new IdDuplicateException(ErrorCode.ID_DUPLICATION);

        Member member = Member.toMember(memberSignUpDto);
        member.encodePassword(passwordEncoder.encode(member.getPassword()));

        return new MemberResponseDto().toMember(memberRepository.save(member));
    }

    public String login(MemberLogInDto memberLogInDto, HttpServletRequest request){
        HttpSession currSession = request.getSession();
        currSession.setAttribute(SessionConstant.LOGIN_MEMBER, memberLogInDto.getLoginId());
        return "성공적으로 로그인 되었습니다.";
    }
}
