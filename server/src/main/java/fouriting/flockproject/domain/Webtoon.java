package fouriting.flockproject.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Webtoon {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WEBTOON_ID")
    private Long id;
    private String name;
    private String author;
    private String image;
    @Lob
    private String details;
    private Platform platform;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    private StarScore starScore;
    @OneToMany(mappedBy = "webtoon")
    private List<Comment> comments = new ArrayList<>();
}
