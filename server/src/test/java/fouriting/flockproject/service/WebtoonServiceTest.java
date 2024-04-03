package fouriting.flockproject.service;

import fouriting.flockproject.domain.Member;
import fouriting.flockproject.domain.StarLike;
import fouriting.flockproject.domain.Webtoon;
import fouriting.flockproject.domain.dto.request.StarRequestDto;
import fouriting.flockproject.domain.dto.request.auth.MemberSignUpDto;
import fouriting.flockproject.domain.enumClass.Genre;
import fouriting.flockproject.domain.enumClass.Platform;
import fouriting.flockproject.domain.enumClass.StarCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WebtoonServiceTest {
    private static Member member;
    private static Webtoon webtoon;
    private static StarLike starLike;

    @BeforeAll
    static void initTest(){
        member = new Member(1L, "abcd", "1234", "Tester");
        webtoon = new Webtoon(1L, "abcd", "Tester", null, "Testing", Platform.NAVER, Genre.무협);
    }

    @Test
    @DisplayName(value = "새로 별점을 추가했을 경우")
    void 별점_등록() {
        // given
        StarRequestDto starRequestDto = new StarRequestDto(5.0);
        starLike = StarLike.toStarLike(starRequestDto, member, webtoon);
        Double targetScore = starRequestDto.getScore();

        // when
        webtoon.updateStar(targetScore);
        webtoon.pushStar();
        member.updateStar(targetScore);

        // then
        Assertions.assertEquals(webtoon.getStarCount(), 1);
        Assertions.assertEquals(webtoon.getStarSum(), 5.0);
        Assertions.assertEquals(member.getScoreFive(), 1);
    }

    @Test
    @DisplayName(value = "기존의 별점을 수정하는 경우")
    void 별점_수정() {
        // given
        StarRequestDto starRequestDto = new StarRequestDto(1.0);
        Double currScore = starLike.getScore();
        Double targetScore = starRequestDto.getScore();

        // when
        member.updateStar(StarCalculator.MAKE_NEGATIVE.calculate(currScore, -1.0));
        webtoon.updateStar(StarCalculator.CALCULATE_DIFFERENCE.calculate(currScore, targetScore));
        starLike.updateStar(targetScore);
        member.updateStar(targetScore);

        // then
        Assertions.assertEquals(webtoon.getStarCount(), 1);
        Assertions.assertEquals(webtoon.getStarSum(), 1.0);
        Assertions.assertEquals(member.getScoreFive(), 0);
        Assertions.assertEquals(member.getScoreOne(), 1);
    }
}