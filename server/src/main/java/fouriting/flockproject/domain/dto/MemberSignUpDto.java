package fouriting.flockproject.domain.dto;

import fouriting.flockproject.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MemberSignUpDto {
    private String loginId;
    private String nickname;
    private String passwd;

    public Member sendMember(){
        return Member.builder()
                .loginId(loginId)
                .nickname(nickname)
                .passwd(passwd)
                .build();
    }
}
