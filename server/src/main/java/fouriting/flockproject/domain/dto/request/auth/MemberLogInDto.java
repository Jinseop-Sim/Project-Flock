package fouriting.flockproject.domain.dto.request.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberLogInDto {
    @NotBlank(message = "아이디는 필수 값입니다.")
    private String loginId;
    @NotBlank(message = "비밀번호는 필수 값입니다.")
    private String passwd;
}