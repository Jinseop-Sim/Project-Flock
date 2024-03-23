package fouriting.flockproject.domain;

import fouriting.flockproject.domain.enumClass.Genre;
import fouriting.flockproject.domain.enumClass.Platform;
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
    @Enumerated(EnumType.STRING)
    private Platform platform;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    private Double starAvg;
    private Double starSum;

    // Foreign Table
    @OneToMany(mappedBy = "webtoon")
    private List<StarLike> stars = new ArrayList<>();
    @OneToMany(mappedBy = "webtoon")
    private List<Comment> comments = new ArrayList<>();

    public void addStar(Double score){
        this.starSum += score;
    }

    public void subStar(Double target){
        this.starSum -= target;
    }

    public void calcStar(){
        if(this.starSum == 0) this.starAvg = 0.0;
        else this.starAvg = starSum / stars.size();
    }
}