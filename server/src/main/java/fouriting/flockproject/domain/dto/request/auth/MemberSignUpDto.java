package fouriting.flockproject.domain.dto.request.auth;

import fouriting.flockproject.domain.enumClass.Authority;
import fouriting.flockproject.domain.Member;
import fouriting.flockproject.domain.enumClass.Title;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberSignUpDto {
    @NotBlank(message = "아이디는 필수 값입니다.")
    private String loginId;
    @NotBlank(message = "닉네임은 필수 값입니다.")
    private String nickname;
    @NotBlank(message = "비밀번호는 필수 값입니다.")
    private String passwd;
}