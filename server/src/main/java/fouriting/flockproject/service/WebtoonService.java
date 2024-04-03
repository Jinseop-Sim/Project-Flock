package fouriting.flockproject.service;

import fouriting.flockproject.domain.Comment;
import fouriting.flockproject.domain.StarLike;
import fouriting.flockproject.domain.dto.request.StarRequestDto;
import fouriting.flockproject.domain.dto.response.session.UserBooleanDto;
import fouriting.flockproject.domain.dto.response.session.UserSessionDto;
import fouriting.flockproject.domain.dto.request.webtoon.WebtoonRequestDto;
import fouriting.flockproject.domain.dto.response.StarResponseDto;
import fouriting.flockproject.domain.dto.response.WebtoonChildCommentDto;
import fouriting.flockproject.domain.dto.response.WebtoonListDto;
import fouriting.flockproject.domain.enumClass.Genre;
import fouriting.flockproject.domain.Member;
import fouriting.flockproject.domain.Webtoon;
import fouriting.flockproject.domain.dto.request.comment.CommentRequestDto;
import fouriting.flockproject.domain.dto.response.infoClass.WebtoonDetailCommentInfo;
import fouriting.flockproject.domain.dto.response.infoClass.WebtoonDetailResponseDto;
import fouriting.flockproject.domain.enumClass.StarCalculator;
import fouriting.flockproject.repository.CommentRepository;
import fouriting.flockproject.repository.MemberRepository;
import fouriting.flockproject.repository.StarRepository;
import fouriting.flockproject.repository.WebtoonRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class WebtoonService {
    private final MemberRepository memberRepository;
    private final WebtoonRepository webtoonRepository;
    private final CommentRepository commentRepository;
    private final StarRepository starRepository;

    public String addComment(Long webtoonId, CommentRequestDto commentRequestDto, UserSessionDto userSessionDto){
        Member findedMember = memberRepository.findByLoginId(userSessionDto.getLoginId()).orElseThrow();
        Webtoon findedWebtoon = webtoonRepository.findById(webtoonId);

        Comment comment = Comment.toCommentWithoutParent(commentRequestDto, findedMember, findedWebtoon);
        commentRepository.save(comment);
        findedWebtoon.addComment(comment);
        findedMember.addComment(comment);

        return "댓글이 성공적으로 등록되었습니다!";
    }

    public String addChildComment(Long webtoonId, Long parentId, CommentRequestDto commentRequestDto, UserSessionDto userSessionDto){
        Member findedMember = memberRepository.findByLoginId(userSessionDto.getLoginId()).orElseThrow();
        Webtoon findedWebtoon = webtoonRepository.findById(webtoonId);
        Comment findedComment = commentRepository.findById(parentId);

        Comment newComment = Comment.toCommentWithParent(commentRequestDto, findedMember, findedWebtoon, findedComment);
        commentRepository.save(newComment);
        findedComment.addChildComment(newComment);
        findedMember.addComment(newComment);

        return "대댓글이 성공적으로 등록되었습니다!";
    }

    @Transactional(readOnly = true)
    public List<WebtoonListDto> showWebtoonList(Genre genre){
        if(genre == Genre.전체)
            return webtoonRepository.findAll().stream()
                    .map(WebtoonListDto::new).collect(Collectors.toList());
        else
            return webtoonRepository.findByGenre(genre).stream()
                    .map(WebtoonListDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public WebtoonDetailResponseDto showWebtoonDetail(Long webtoonId, UserBooleanDto userBooleanDto){
        Webtoon findedWebtoon = webtoonRepository.findById(webtoonId);
        Double currStar = findedWebtoon.getStarAvg();

        if(userBooleanDto.getIsLoggedIn()) {
            Member findedMember = memberRepository.findByLoginId(userBooleanDto.getLoginId()).orElseThrow();
            StarLike findedStarLike = starRepository.findByMemberIdAndWebtoonId(findedMember.getLoginId(), webtoonId).orElse(null);
            if(findedStarLike == null)
                currStar = 0.0;
            else
                currStar = findedStarLike.getScore();
        } // 별점 불러오는 로직

        List<WebtoonDetailCommentInfo> detailCommentList = new ArrayList<>();
        HashMap<Long, WebtoonDetailCommentInfo> findChildMap = new HashMap<>();
        for(Comment comment : commentRepository.findByPostId(webtoonId)){
            WebtoonDetailCommentInfo webtoonDetailCommentInfo = new WebtoonDetailCommentInfo(comment);
            findChildMap.put(comment.getId(), webtoonDetailCommentInfo);

            if(comment.getParentComment() != null){
                findChildMap.get(comment.getParentComment().getId())
                        .addChildComment(new WebtoonChildCommentDto(comment));
            }
            else
                detailCommentList.add(webtoonDetailCommentInfo);
        }
        // 댓글 불러오는 로직 (DB 접근?)
        
        return WebtoonDetailResponseDto.toWebtoonDetailDto(findedWebtoon, detailCommentList, currStar);
    }

    @Transactional
    public StarResponseDto addStarToWebtoon(Long webtoonId, StarRequestDto starRequestDto, UserSessionDto userSessionDto){
        Member findedMember = memberRepository.findByLoginId(userSessionDto.getLoginId()).orElseThrow();
        StarLike findedStar = starRepository.findByMemberIdAndWebtoonId(findedMember.getLoginId(), webtoonId).orElseThrow();
        Webtoon findedWebtoon = webtoonRepository.findById(webtoonId);

        Double targetScore = starRequestDto.getScore();
        // 내가 표시했던 별점이 5점이나 1점이면 빼야함.
        StarLike starLike = StarLike.toStarLike(starRequestDto, findedMember, findedWebtoon);
        findedWebtoon.updateStar(targetScore);
        findedWebtoon.pushStar(); // 누른 사람 1명 추가

        findedMember.updateStar(targetScore);

        starRepository.save(starLike);


        return new StarResponseDto(findedStar.getScore());
    }

    @Transactional
    public StarResponseDto updateStarToWebtoon(Long webtoonId, StarRequestDto starRequestDto, UserSessionDto userSessionDto){
        Member findedMember = memberRepository.findByLoginId(userSessionDto.getLoginId()).orElseThrow();
        StarLike findedStar = starRepository.findByMemberIdAndWebtoonId(findedMember.getLoginId(), webtoonId).orElseThrow();
        Webtoon findedWebtoon = webtoonRepository.findById(webtoonId);

        Double currScore = findedStar.getScore();
        Double targetScore = starRequestDto.getScore();

        findedMember.updateStar(StarCalculator.MAKE_NEGATIVE.calculate(currScore, -1.0));
        findedWebtoon.updateStar(StarCalculator.CALCULATE_DIFFERENCE.calculate(currScore, targetScore));
        findedStar.updateStar(targetScore);

        // 내가 바꾸려는 별점이 5점이나 1점이면 추가.
        findedMember.updateStar(targetScore);

        return new StarResponseDto(currScore);
    }

    @Transactional(readOnly = true)
    public List<WebtoonListDto> searchWebtoon(WebtoonRequestDto webtoonRequestDto){
        // 검색한 제목이 들어가는 웹툰 List를 받아온다.
        return webtoonRepository.findByName(webtoonRequestDto.getName()).stream()
                .map(WebtoonListDto::new).collect(Collectors.toList());
    }
}
