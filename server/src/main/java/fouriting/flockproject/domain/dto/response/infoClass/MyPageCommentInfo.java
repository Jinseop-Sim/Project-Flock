package fouriting.flockproject.domain.dto.response.infoClass;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MyPageCommentInfo {
    private String name;
    private String contents;
    private LocalDateTime createTime;
}