package fouriting.flockproject.domain.dto.request;

import fouriting.flockproject.domain.enumClass.Authority;
import fouriting.flockproject.domain.Member;
import fouriting.flockproject.domain.enumClass.Title;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@Builder
public class MemberSignUpDto {
    @NotBlank(message = "아이디는 필수 값입니다.")
    private String loginId;
    @NotBlank(message = "닉네임은 필수 값입니다.")
    private String nickname;
    @NotBlank(message = "이름은 필수 값입니다.")
    private String passwd;

    public Member sendMember(PasswordEncoder passwordEncoder){
        return Member.builder()
                .loginId(loginId)
                .nickname(nickname)
                .passwd(passwordEncoder.encode(passwd))
                .authority(Authority.ROLE_USER)
                .title(Title.새싹)
                .build();
    }
}