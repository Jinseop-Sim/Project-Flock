package fouriting.flockproject.domain.dto.response.infoClass;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentInfo {
    private String contents;
    private LocalDateTime createTime;
}