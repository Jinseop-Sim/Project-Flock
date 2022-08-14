package fouriting.flockproject.service;

import fouriting.flockproject.config.security.SecurityUtil;
import fouriting.flockproject.domain.Comment;
import fouriting.flockproject.domain.Member;
import fouriting.flockproject.domain.Webtoon;
import fouriting.flockproject.domain.dto.response.infoClass.CommentInfo;
import fouriting.flockproject.domain.dto.response.MyPageResponseDto;
import fouriting.flockproject.domain.dto.response.infoClass.WebtoonInfo;
import fouriting.flockproject.domain.dto.response.WebtoonSearchDto;
import fouriting.flockproject.repository.CommentRepository;
import fouriting.flockproject.repository.MemberRepository;
import fouriting.flockproject.repository.WebtoonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final WebtoonRepository webtoonRepository;

    @Transactional
    public MyPageResponseDto showMyPage() {
        List<CommentInfo> commentList = new ArrayList<>();
        // 현재 요청을 보낸 사람의 member id를 딴다.
        Member findedMember = memberRepository.findById(SecurityUtil.getCurrnetMemberId()).get();

        for (Comment comment : findedMember.getMyComments()) {
            commentList.add(new CommentInfo(comment.getContents(), comment.getPostTime()));
        }

        return new MyPageResponseDto(findedMember.getNickname(),
                                     findedMember.getTitle(),
                                     commentList);
    }

    @Transactional
    public WebtoonSearchDto searchWebtoon(String name){
        System.out.println(name);
        List<WebtoonInfo> webtoonInfoList = new ArrayList<>();
        // 검색한 제목이 들어가는 웹툰 List를 받아온다.
        List<Webtoon> webtoonList = webtoonRepository.findByName(name);
        for (Webtoon webtoon : webtoonList) {
            webtoonInfoList.add(new WebtoonInfo(webtoon.getName(),
                                                webtoon.getAuthor(),
                                                webtoon.getImage()));
        }

        return new WebtoonSearchDto(webtoonInfoList);
    }
}
