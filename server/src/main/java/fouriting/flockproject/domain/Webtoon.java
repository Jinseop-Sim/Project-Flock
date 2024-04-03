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
    private Long starCount;

    // Foreign Table
    @OneToMany(mappedBy = "webtoon")
    private List<Comment> comments = new ArrayList<>();

    public Webtoon(Long id, String name, String author, String image, String details, Platform platform, Genre genre){
        this.id = id;
        this.name = name;
        this.author = author;
        this.image = image;
        this.details = details;
        this.platform = platform;
        this.genre = genre;
        this.starAvg = 0.0;
        this.starSum = 0.0;
        this.starCount = 0L;
    }

    public void updateStar(Double score){
        this.starSum += score;
        calculateStar();
    }
    public void pushStar(){
        this.starCount++;
    }

    public void addComment(Comment comment){
        this.comments.add(comment);
    }

    public void calculateStar(){
        if(this.starSum == 0) this.starAvg = 0.0;
        else this.starAvg = starSum / starCount;
    }
}