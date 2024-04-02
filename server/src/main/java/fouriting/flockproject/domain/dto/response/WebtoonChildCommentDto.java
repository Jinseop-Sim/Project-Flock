package fouriting.flockproject.domain.dto.response;

import fouriting.flockproject.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class WebtoonChildCommentDto {
    private String name;
    private String contents;
    private LocalDateTime createTime;

    public WebtoonChildCommentDto(Comment comment){
        this.name = comment.getAuthor();
        this.contents = comment.getContents();
        this.createTime = comment.getPostTime();
    }
}
