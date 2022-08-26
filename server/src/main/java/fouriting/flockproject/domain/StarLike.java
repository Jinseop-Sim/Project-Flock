package fouriting.flockproject.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StarLike {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STAR_ID")
    private Long id;
    private Double score;

    // Foreign Key
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "WEBTOON_ID")
    private Webtoon webtoon;

    public void postStar(){
        member.getMyStars().add(this);
        webtoon.getStars().add(this);
    }

    public void updateStar(Double score){
        this.score = score;
    }
}
