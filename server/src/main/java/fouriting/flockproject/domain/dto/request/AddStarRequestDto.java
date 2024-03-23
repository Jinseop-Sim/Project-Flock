package fouriting.flockproject.domain.dto.request;

import fouriting.flockproject.domain.Member;
import fouriting.flockproject.domain.StarLike;
import fouriting.flockproject.domain.Webtoon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AddStarRequestDto {
    @NotNull
    Double score;

    public StarLike sendStarLike(Member member, Webtoon webtoon){
        return StarLike.builder()
                .member(member)
                .webtoon(webtoon)
                .score(score)
                .build();
    }
}
