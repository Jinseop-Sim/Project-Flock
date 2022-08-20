package fouriting.flockproject.domain.dto.response;

import fouriting.flockproject.domain.Comment;
import fouriting.flockproject.domain.Member;
import fouriting.flockproject.domain.Webtoon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CommentResponseDto {
    private String memberName;
    private String webtoonName;
    private String contents;
    private String createTime;

    public CommentResponseDto sendCommentDto(Comment comment){
        return CommentResponseDto.builder()
                .memberName(comment.getMember().getNickname())
                .webtoonName(comment.getWebtoon().getName())
                .contents(comment.getContents())
                .createTime(comment.getPostTime())
                .build();
    }
}