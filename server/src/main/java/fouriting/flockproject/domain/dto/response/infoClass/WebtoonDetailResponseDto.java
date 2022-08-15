package fouriting.flockproject.domain.dto.response.infoClass;

import fouriting.flockproject.domain.enumClass.Genre;
import fouriting.flockproject.domain.enumClass.Platform;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class WebtoonDetailResponseDto {
    private String name;
    private String author;
    private String image;
    private Genre genre;
    private String details;
    private Platform platform;
    private Double avgScore;
    private Double starScore;
    private List<WebtoonDetailCommentInfo> comments;
}
