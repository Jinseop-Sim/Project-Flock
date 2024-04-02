package fouriting.flockproject.domain.dto.response.session;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserBooleanDto {
    private Boolean isLoggedIn;
    private String loginId;
}
