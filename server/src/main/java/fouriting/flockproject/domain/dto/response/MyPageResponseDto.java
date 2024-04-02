package fouriting.flockproject.domain.dto.response;

import fouriting.flockproject.domain.Member;
import fouriting.flockproject.domain.enumClass.Title;
import fouriting.flockproject.domain.dto.response.infoClass.MyPageCommentInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyPageResponseDto {
    private String nickname;
    private Title title;
    private List<MyPageCommentInfo> commentInfo;

    public MyPageResponseDto(Member member, List<MyPageCommentInfo> commentList){
        this.nickname = member.getNickname();
        this.title = member.getTitle();
        this.commentInfo = commentList;
    }
}