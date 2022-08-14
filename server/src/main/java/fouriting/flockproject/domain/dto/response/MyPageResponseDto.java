package fouriting.flockproject.domain.dto.response;

import fouriting.flockproject.domain.Title;
import fouriting.flockproject.domain.dto.response.infoClass.CommentInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyPageResponseDto {
    private String nickname;
    private Title title;
    private List<CommentInfo> commentInfo;

    public MyPageResponseDto sendMyPage() {
        return MyPageResponseDto.builder()
                .title(title)
                .nickname(nickname)
                .commentInfo(commentInfo)
                .build();
    }
}