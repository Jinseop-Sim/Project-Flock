package fouriting.flockproject.domain.dto;

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
    private Long id;
    private String loginId;
    private String nickname;
    private String passwd;

    public MemberResponseDto memberResponse(Member member){
        return MemberResponseDto.builder()
                .id(member.getId())
                .loginId(member.getLoginId())
                .nickname(member.getNickname())
                .passwd(member.getPasswd())
                .build();
    }
}
