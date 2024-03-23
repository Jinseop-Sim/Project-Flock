package fouriting.flockproject.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@Builder
public class MemberLogInDto {
    @NotBlank(message = "아이디는 필수 값입니다.")
    private String loginId;
    @NotBlank(message = "비밀번호는 필수 값입니다.")
    private String passwd;

    public UsernamePasswordAuthenticationToken sendAuthentication(){
        return new UsernamePasswordAuthenticationToken(loginId, passwd);
    }
}