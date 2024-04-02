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

    public void addStar(Double score){
        this.starSum += score;
    }

    public void subStar(Double target){
        this.starSum -= target;
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