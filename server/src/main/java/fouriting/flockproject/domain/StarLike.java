package fouriting.flockproject.domain;

import fouriting.flockproject.domain.dto.request.AddStarRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    public void updateStar(Double score){
        this.score = score;
    }

    private StarLike(AddStarRequestDto addStarRequestDto, Member member, Webtoon webtoon){
        this.member = member;
        this.webtoon = webtoon;
        this.score = addStarRequestDto.getScore();
    }

    public static StarLike toStarLike(AddStarRequestDto addStarRequestDto, Member member, Webtoon webtoon){
        return new StarLike(addStarRequestDto, member, webtoon);
    }
}
