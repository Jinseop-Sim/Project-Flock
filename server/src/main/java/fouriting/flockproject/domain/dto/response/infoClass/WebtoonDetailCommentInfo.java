package fouriting.flockproject.domain.dto.response.infoClass;

import fouriting.flockproject.domain.Comment;
import fouriting.flockproject.domain.Webtoon;
import fouriting.flockproject.domain.dto.response.WebtoonChildCommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class WebtoonDetailCommentInfo {
    private String name;
    private String contents;
    private LocalDateTime createTime;
    private List<WebtoonChildCommentDto> childCommentDtos = new ArrayList<>();

    public WebtoonDetailCommentInfo(Comment comment){
        this.name = comment.getAuthor();
        this.contents = comment.getContents();
        this.createTime = comment.getPostTime();
    }

    public void addChildComment(WebtoonChildCommentDto webtoonChildCommentDto){
        this.childCommentDtos.add(webtoonChildCommentDto);
    }
}
