package fouriting.flockproject.service;

import fouriting.flockproject.config.jwt.TokenProvider;
import fouriting.flockproject.domain.Member;
import fouriting.flockproject.domain.RefreshToken;
import fouriting.flockproject.domain.dto.request.MemberLogInDto;
import fouriting.flockproject.domain.dto.request.TokenRequestDto;
import fouriting.flockproject.domain.dto.response.MemberResponseDto;
import fouriting.flockproject.domain.dto.request.MemberSignUpDto;
import fouriting.flockproject.domain.dto.response.MemberTokenDto;
import fouriting.flockproject.exception.ErrorCode;
import fouriting.flockproject.exception.custom.IdDuplicateException;
import fouriting.flockproject.exception.custom.IdNotExistException;
import fouriting.flockproject.exception.custom.PasswdNotMatchException;
import fouriting.flockproject.repository.MemberRepository;
import fouriting.flockproject.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public MemberResponseDto signUp(MemberSignUpDto memberSignUpDto){
        if(memberRepository.findByLoginId(memberSignUpDto.getLoginId()).isPresent())
            throw new IdDuplicateException("이미 존재하는 ID입니다.", ErrorCode.ID_DUPLICATION);

        Member member = memberSignUpDto.sendMember(passwordEncoder);
        return new MemberResponseDto().memberResponse(memberRepository.save(member));
    }

    @Transactional
    public MemberTokenDto logIn(MemberLogInDto memberLogInDto){
        Optional<Member> findedMember = memberRepository.findByLoginId(memberLogInDto.getLoginId());

        findedMember.orElseThrow(() -> new IdNotExistException("존재하지 않는 User입니다.", ErrorCode.ID_NOT_EXIST));
        if(!passwordEncoder.matches(memberLogInDto.getPasswd(), findedMember.get().getPasswd()))
            throw new PasswdNotMatchException("Password가 일치하지 않습니다!", ErrorCode.PW_NOT_MATCH);

        // Login Id / Pw를 기반으로 권한 Token 생성
        UsernamePasswordAuthenticationToken authenticationToken = memberLogInDto.sendAuthentication();

        // 실제로 검증하는 단계입니다.
        // authenticate Method가 실행이 되면서 검증.
        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);

        // 인증 정보를 기반으로 JWT 토큰 생성
        MemberTokenDto memberTokenDto = tokenProvider.generateTokenDto(authentication);

        // Refresh Token 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(memberTokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 토큰 발급!
        return memberTokenDto;
    }

    @Transactional
    public MemberTokenDto reissue(TokenRequestDto tokenRequestDto){
        if(!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())){
            throw new RuntimeException("유효하지 않은 Refresh Token 입니다.");
        }
        // 현재 로그인 한 Access Token을 떼온다.
        // Access Token에서 Member Login Id를 가져온다.
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 저장소에서 Member ID를 기반으로 Refresh Token을 가져옵니다.
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자 입니다."));

        // 같은 토큰인지 검사!
        if(!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())){
            throw new RuntimeException("Refresh Token의 유저 정보가 일치하지 않습니다.");
        }

        // 동일한 토큰이라면 다시 발급해줍니다.
        MemberTokenDto memberTokenDto = tokenProvider.generateTokenDto(authentication);

        // 저장소 정보를 업데이트 해줍니다.
        RefreshToken newRefreshToken = refreshToken.updateValue(memberTokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        return memberTokenDto;
    }
}
