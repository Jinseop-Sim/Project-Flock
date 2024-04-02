package fouriting.flockproject.domain.dto.response.infoClass;

import fouriting.flockproject.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MyPageCommentInfo {
    private String name;
    private String contents;
    private LocalDateTime createTime;

    public MyPageCommentInfo(Comment comment){
        this.name = comment.getAuthor();
        this.contents = comment.getContents();
        this.createTime = comment.getPostTime();
    }
}