package fouriting.flockproject.service;

import fouriting.flockproject.domain.Member;
import fouriting.flockproject.domain.dto.request.MemberLogInDto;
import fouriting.flockproject.domain.dto.response.MemberResponseDto;
import fouriting.flockproject.domain.dto.request.MemberSignUpDto;
import fouriting.flockproject.exception.ErrorCode;
import fouriting.flockproject.exception.custom.IdDuplicateException;
import fouriting.flockproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberResponseDto signUp(MemberSignUpDto memberSignUpDto){
        if(memberRepository.findByLoginId(memberSignUpDto.getLoginId()).isPresent())
            throw new IdDuplicateException("이미 존재하는 ID입니다.", ErrorCode.ID_DUPLICATION);

        Member member = memberSignUpDto.sendMember(passwordEncoder);
        return new MemberResponseDto().toMember(memberRepository.save(member));
    }

    public String login(MemberLogInDto memberLogInDto, HttpServletRequest request){
        request.getSession().invalidate(); // 세션 파기

        // 새 세션 생성
        HttpSession newSession = request.getSession();
        newSession.setAttribute("loginUser", memberRepository.findByLoginId(memberLogInDto.getLoginId()));
        newSession.setMaxInactiveInterval(1800); // 30min
        return "성공적으로 로그인 되었습니다.";
    }
}
