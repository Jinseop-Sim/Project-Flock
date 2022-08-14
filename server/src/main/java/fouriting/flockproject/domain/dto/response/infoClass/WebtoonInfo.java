package fouriting.flockproject.domain.dto.response.infoClass;

import fouriting.flockproject.domain.Genre;
import fouriting.flockproject.domain.Platform;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WebtoonInfo {
    private String name;
    private String author;
    private String image;
    private Genre genre;
    private String details;
    private Platform platform;

    public WebtoonInfo(String name, String author, String image) {
        this.name = name;
        this.author = author;
        this.image = image;
    }
}
