package fouriting.flockproject.domain.dto.response;

import fouriting.flockproject.domain.Webtoon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WebtoonListDto {
    private Long id;
    private String name;
    private String author;
    private String image;

    public WebtoonListDto(Webtoon webtoon){
        this.id = webtoon.getId();
        this.name = webtoon.getName();
        this.author = webtoon.getAuthor();
        this.image = webtoon.getImage();
    }
}
