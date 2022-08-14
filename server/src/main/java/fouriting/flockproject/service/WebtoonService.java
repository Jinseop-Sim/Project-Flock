package fouriting.flockproject.service;

import fouriting.flockproject.config.security.SecurityUtil;
import fouriting.flockproject.domain.Comment;
import fouriting.flockproject.domain.Genre;
import fouriting.flockproject.domain.Member;
import fouriting.flockproject.domain.Webtoon;
import fouriting.flockproject.domain.dto.request.CommentRequestDto;
import fouriting.flockproject.domain.dto.response.CommentResponseDto;
import fouriting.flockproject.domain.dto.response.infoClass.WebtoonInfo;
import fouriting.flockproject.domain.dto.response.WebtoonSearchDto;
import fouriting.flockproject.repository.CommentRepository;
import fouriting.flockproject.repository.MemberRepository;
import fouriting.flockproject.repository.WebtoonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class WebtoonService {
    private final MemberRepository memberRepository;
    private final WebtoonRepository webtoonRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponseDto addComment(Long webtoonId, CommentRequestDto commentRequestDto){
        Member findedMember = memberRepository.findById(SecurityUtil.getCurrnetMemberId()).get();
        Webtoon findedWebtoon = webtoonRepository.findById(webtoonId).get();
        Comment comment = commentRequestDto.sendComment(findedMember, findedWebtoon);
        commentRepository.save(comment, findedMember);

        return new CommentResponseDto().sendCommentDto(comment);
    }

    @Transactional
    public WebtoonSearchDto showWebtoonList(Genre genre){
        List<WebtoonInfo> webtoonInfos = new ArrayList<>();
        List<Webtoon> findedWebtoons = webtoonRepository.findByGenre(genre);
        for (Webtoon findedWebtoon : findedWebtoons) {
            webtoonInfos.add(new WebtoonInfo(findedWebtoon.getName(), findedWebtoon.getAuthor(), findedWebtoon.getImage()));
        }

        return new WebtoonSearchDto(webtoonInfos);
    }

    @Transactional
    public WebtoonInfo showWebtoonDetail(Long webtoonId){
        Webtoon findedWebtoon = webtoonRepository.findById(webtoonId).get();

        return new WebtoonInfo(findedWebtoon.getName(), findedWebtoon.getAuthor(), findedWebtoon.getImage(),
                               findedWebtoon.getGenre(), findedWebtoon.getDetails(), findedWebtoon.getPlatform());
    }
}
