package fouriting.flockproject.service;

import fouriting.flockproject.config.security.SecurityUtil;
import fouriting.flockproject.domain.Comment;
import fouriting.flockproject.domain.StarLike;
import fouriting.flockproject.domain.dto.request.WebtoonRequestDto;
import fouriting.flockproject.domain.dto.response.StarResponseDto;
import fouriting.flockproject.domain.enumClass.Genre;
import fouriting.flockproject.domain.Member;
import fouriting.flockproject.domain.Webtoon;
import fouriting.flockproject.domain.dto.request.AddStarRequestDto;
import fouriting.flockproject.domain.dto.request.CommentRequestDto;
import fouriting.flockproject.domain.dto.response.CommentResponseDto;
import fouriting.flockproject.domain.dto.response.infoClass.WebtoonDetailCommentInfo;
import fouriting.flockproject.domain.dto.response.infoClass.WebtoonDetailResponseDto;
import fouriting.flockproject.domain.dto.response.infoClass.WebtoonInfo;
import fouriting.flockproject.domain.dto.response.WebtoonSearchDto;
import fouriting.flockproject.repository.CommentRepository;
import fouriting.flockproject.repository.MemberRepository;
import fouriting.flockproject.repository.StarRepository;
import fouriting.flockproject.repository.WebtoonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static java.lang.Math.abs;

@Service
@AllArgsConstructor
public class WebtoonService {
    private final MemberRepository memberRepository;
    private final WebtoonRepository webtoonRepository;
    private final CommentRepository commentRepository;
    private final StarRepository starRepository;

    @Transactional
    public CommentResponseDto addComment(Long webtoonId, CommentRequestDto commentRequestDto){
        Member findedMember = memberRepository.findById(SecurityUtil.getCurrnetMemberId()).get();
        Webtoon findedWebtoon = webtoonRepository.findById(webtoonId);
        Comment comment = commentRequestDto.sendComment(findedMember, findedMember.getNickname(), findedWebtoon);
        comment.addToMemberAndWebtoon();
        commentRepository.save(comment);
        findedMember.updateTitle();

        return new CommentResponseDto().sendCommentDto(comment);
    }

    @Transactional
    public WebtoonSearchDto showWebtoonList(Genre genre){
        List<WebtoonInfo> webtoonInfos = new ArrayList<>();
        List<Webtoon> findedWebtoons = webtoonRepository.findByGenre(genre);
        for (Webtoon findedWebtoon : findedWebtoons) {
            webtoonInfos.add(new WebtoonInfo(findedWebtoon.getId(), findedWebtoon.getName(), findedWebtoon.getAuthor(), findedWebtoon.getImage()));
        }

        return new WebtoonSearchDto(webtoonInfos);
    }

    @Transactional
    public WebtoonDetailResponseDto showWebtoonDetail(Long webtoonId, HttpServletRequest request){
        Double tempStar = 0.0;
        if(request.getHeader("Authorization") != null) {
            Optional<StarLike> findedStar = starRepository.findByMemberId(SecurityUtil.getCurrnetMemberId(), webtoonId);

            if (findedStar.isPresent()) {
                    tempStar = findedStar.get().getScore();
            }
        }

        List<WebtoonDetailCommentInfo> commentInfos = new ArrayList<>();
        Webtoon findedWebtoon = webtoonRepository.findById(webtoonId);

        for (Comment comment : findedWebtoon.getComments()) {
            commentInfos.add(new WebtoonDetailCommentInfo(comment.getAuthor(),
                                                          comment.getContents(),
                                                          comment.getPostTime()));
        }

        findedWebtoon.calcStar();

        return new WebtoonDetailResponseDto(findedWebtoon.getName(), findedWebtoon.getAuthor(),
                findedWebtoon.getImage(), findedWebtoon.getGenre(),
                findedWebtoon.getDetails(), findedWebtoon.getPlatform(), findedWebtoon.getStarAvg(),
                tempStar, commentInfos);
    }

    @Transactional
    public StarResponseDto addStarToWebtoon(Long webtoonId, AddStarRequestDto addStarRequestDto){
        Member findedMember = memberRepository.findById(SecurityUtil.getCurrnetMemberId()).get();
        Optional<StarLike> findedStar = starRepository.findByMemberId(findedMember.getId(), webtoonId);

        if(findedStar.isPresent()) {
            Webtoon findedWebtoon = webtoonRepository.findById(webtoonId);
            findedWebtoon.subStar(abs(findedStar.get().getScore() - addStarRequestDto.getScore()));
            findedStar.get().updateStar(addStarRequestDto.getScore());
        }

        else {
            Webtoon findedWebtoon = webtoonRepository.findById(webtoonId);
            StarLike starLike = addStarRequestDto.sendStarLike(findedMember, findedWebtoon);
            starLike.postStar(); findedWebtoon.addStar(addStarRequestDto.getScore());
            starRepository.save(starLike);

            return new StarResponseDto(starLike.getScore());
        }

        return new StarResponseDto(findedStar.get().getScore());
    }

    @Transactional
    public WebtoonSearchDto searchWebtoon(WebtoonRequestDto webtoonRequestDto){
        List<WebtoonInfo> webtoonInfoList = new ArrayList<>();
        // 검색한 제목이 들어가는 웹툰 List를 받아온다.
        List<Webtoon> webtoonList = webtoonRepository.findByName(webtoonRequestDto.getName());
        for (Webtoon webtoon : webtoonList) {
            webtoonInfoList.add(new WebtoonInfo(
                    webtoon.getId(),
                    webtoon.getName(),
                    webtoon.getAuthor(),
                    webtoon.getImage()));
        }

        return new WebtoonSearchDto(webtoonInfoList);
    }
}
