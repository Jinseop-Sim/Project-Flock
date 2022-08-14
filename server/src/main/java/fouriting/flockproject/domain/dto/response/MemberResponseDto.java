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

    public MemberResponseDto memberResponse(Member member){
        return MemberResponseDto.builder()
                .loginId(member.getLoginId())
                .nickname(member.getNickname())
                .build();
    }
}