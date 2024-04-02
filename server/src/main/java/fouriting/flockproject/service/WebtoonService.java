package fouriting.flockproject.service;

import fouriting.flockproject.config.SessionConstant;
import fouriting.flockproject.domain.Comment;
import fouriting.flockproject.domain.StarLike;
import fouriting.flockproject.domain.dto.request.webtoon.WebtoonRequestDto;
import fouriting.flockproject.domain.dto.response.StarResponseDto;
import fouriting.flockproject.domain.dto.response.WebtoonChildCommentDto;
import fouriting.flockproject.domain.dto.response.WebtoonListDto;
import fouriting.flockproject.domain.enumClass.Genre;
import fouriting.flockproject.domain.Member;
import fouriting.flockproject.domain.Webtoon;
import fouriting.flockproject.domain.dto.request.AddStarRequestDto;
import fouriting.flockproject.domain.dto.request.comment.CommentRequestDto;
import fouriting.flockproject.domain.dto.response.infoClass.WebtoonDetailCommentInfo;
import fouriting.flockproject.domain.dto.response.infoClass.WebtoonDetailResponseDto;
import fouriting.flockproject.repository.CommentRepository;
import fouriting.flockproject.repository.MemberRepository;
import fouriting.flockproject.repository.StarRepository;
import fouriting.flockproject.repository.WebtoonRepository;
import fouriting.flockproject.utility.SessionUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class WebtoonService {
    private final MemberRepository memberRepository;
    private final WebtoonRepository webtoonRepository;
    private final CommentRepository commentRepository;
    private final StarRepository starRepository;

    public String addComment(Long webtoonId, CommentRequestDto commentRequestDto, HttpServletRequest request){
        String currentMemberId = SessionUtil.getCurrentMemberId(request);
        Member findedMember = memberRepository.findByLoginId(currentMemberId).orElseThrow();
        Webtoon findedWebtoon = webtoonRepository.findById(webtoonId);

        Comment comment = Comment.toCommentWithoutParent(commentRequestDto, findedMember, findedWebtoon);
        commentRepository.save(comment);
        findedWebtoon.addComment(comment);
        findedMember.updateTitle(comment);

        return "댓글이 성공적으로 등록되었습니다!";
    }

    public String addChildComment(Long webtoonId, Long parentId, CommentRequestDto commentRequestDto, HttpServletRequest request){
        String currentMemberId = SessionUtil.getCurrentMemberId(request);
        Member findedMember = memberRepository.findByLoginId(currentMemberId).orElseThrow();
        Webtoon findedWebtoon = webtoonRepository.findById(webtoonId);
        Comment findedComment = commentRepository.findById(parentId);

        Comment newComment = Comment.toCommentWithParent(commentRequestDto, findedMember, findedWebtoon, findedComment);
        commentRepository.save(newComment);
        findedComment.addChildComment(newComment);
        findedMember.updateTitle(newComment);

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
    public WebtoonDetailResponseDto showWebtoonDetail(Long webtoonId, HttpServletRequest request){
        Webtoon findedWebtoon = webtoonRepository.findById(webtoonId);
        Double currStar = findedWebtoon.getStarAvg();

        HttpSession session = request.getSession(false);
        if(session != null) {
            String findedMember = (String) session.getAttribute(SessionConstant.LOGIN_MEMBER);
            StarLike findedStarLike = starRepository.findByMemberIdAndWebtoonId(findedMember, webtoonId).orElse(null);
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
    public StarResponseDto addStarToWebtoon(Long webtoonId, AddStarRequestDto addStarRequestDto, HttpServletRequest request){
        String currentMemberId = SessionUtil.getCurrentMemberId(request);
        Member findedMember = memberRepository.findByLoginId(currentMemberId).get();
        Optional<StarLike> findedStar = starRepository.findByMemberIdAndWebtoonId(findedMember.getLoginId(), webtoonId);

        if(findedStar.isPresent()) { // 이 때는, 이미 별점을 표시하고 업데이트 할 때.
            Webtoon findedWebtoon = webtoonRepository.findById(webtoonId);
            // 내가 표시했던 별점이 5점이나 1점이면 빼야함.
            if(findedStar.get().getScore() == 5) findedMember.subStarFive();
            if(findedStar.get().getScore() == 1) findedMember.subStarOne();

            if(findedStar.get().getScore() > addStarRequestDto.getScore())
                findedWebtoon.subStar(findedStar.get().getScore() - addStarRequestDto.getScore());
            else findedWebtoon.addStar(addStarRequestDto.getScore() - findedStar.get().getScore());
            // 이 부분에서 웹툰에 이미 등록된 점수를 빼고 더한다.
            findedStar.get().updateStar(addStarRequestDto.getScore());

            // 내가 바꾸려는 별점이 5점이나 1점이면 추가.
            if(addStarRequestDto.getScore() == 5) findedMember.addStarFive();
            if(addStarRequestDto.getScore() == 1) findedMember.addStarOne();
        }
        else { // 아직 별점을 등록을 안했을 때
            Webtoon findedWebtoon = webtoonRepository.findById(webtoonId);
            StarLike starLike = StarLike.toStarLike(addStarRequestDto, findedMember, findedWebtoon);
            findedWebtoon.addStar(addStarRequestDto.getScore());
            findedWebtoon.calculateStar(); // 여기서 계산 해야됨
            starRepository.save(starLike);
            // 내가 바꾸려는 별점이 5점이나 1점이면 추가.
            if(addStarRequestDto.getScore() == 5) findedMember.addStarFive();
            if(addStarRequestDto.getScore() == 1) findedMember.addStarOne();
            findedWebtoon.pushStar(); // 누른 사람 1명 추가

            return new StarResponseDto(starLike.getScore());
        }

        return new StarResponseDto(findedStar.get().getScore());
    }

    @Transactional(readOnly = true)
    public List<WebtoonListDto> searchWebtoon(WebtoonRequestDto webtoonRequestDto){
        // 검색한 제목이 들어가는 웹툰 List를 받아온다.
        return webtoonRepository.findByName(webtoonRequestDto.getName()).stream()
                .map(WebtoonListDto::new).collect(Collectors.toList());
    }
}
