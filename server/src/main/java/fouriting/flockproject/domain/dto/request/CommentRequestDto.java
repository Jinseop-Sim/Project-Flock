package fouriting.flockproject.domain.dto.request;

import fouriting.flockproject.domain.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequestDto {
    private String contents;
    private String createTime;

    public Comment sendComment(Member member, String author, Webtoon webtoon){
        return Comment.builder()
                .member(member)
                .webtoon(webtoon)
                .author(author)
                .contents(contents)
                .postTime(createTime)
                .build();
    }
}