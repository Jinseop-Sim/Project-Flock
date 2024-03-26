package fouriting.flockproject.domain.dto.response;

import fouriting.flockproject.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponseDto {
    private String loginId;
    private String nickname;

    private MemberResponseDto(Member member){
        this.loginId = member.getLoginId();
        this.nickname = member.getNickname();
    }
    public MemberResponseDto toMember(Member member){
        return new MemberResponseDto(member);
    }
}