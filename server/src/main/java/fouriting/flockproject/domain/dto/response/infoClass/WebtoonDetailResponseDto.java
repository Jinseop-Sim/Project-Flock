package fouriting.flockproject.domain.dto.response.infoClass;

import fouriting.flockproject.domain.Webtoon;
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
    private Double starScore;
    private List<WebtoonDetailCommentInfo> comments;

    private WebtoonDetailResponseDto(Webtoon webtoon, List<WebtoonDetailCommentInfo> comments, Double currStar){
        this.name = webtoon.getName();
        this.author = webtoon.getAuthor();
        this.image = webtoon.getImage();
        this.genre = webtoon.getGenre();
        this.details = webtoon.getDetails();
        this.platform = webtoon.getPlatform();
        this.starScore = currStar;
        this.comments = comments;
    }

    public static WebtoonDetailResponseDto toWebtoonDetailDto(Webtoon webtoon, List<WebtoonDetailCommentInfo> comments, Double currStar){
        return new WebtoonDetailResponseDto(webtoon, comments, currStar);
    }
}
