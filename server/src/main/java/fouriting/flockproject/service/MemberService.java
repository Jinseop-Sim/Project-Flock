package fouriting.flockproject.service;

import fouriting.flockproject.config.security.SecurityUtil;
import fouriting.flockproject.domain.Comment;
import fouriting.flockproject.domain.Member;
import fouriting.flockproject.domain.Webtoon;
import fouriting.flockproject.domain.dto.request.WebtoonRequestDto;
import fouriting.flockproject.domain.dto.response.infoClass.MyPageCommentInfo;
import fouriting.flockproject.domain.dto.response.MyPageResponseDto;
import fouriting.flockproject.domain.dto.response.infoClass.WebtoonInfo;
import fouriting.flockproject.domain.dto.response.WebtoonSearchDto;
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
    private final WebtoonRepository webtoonRepository;

    @Transactional
    public MyPageResponseDto showMyPage() {
        List<MyPageCommentInfo> commentList = new ArrayList<>();
        // 현재 요청을 보낸 사람의 member id를 딴다.
        Member findedMember = memberRepository.findById(SecurityUtil.getCurrnetMemberId()).get();

        for (Comment comment : findedMember.getMyComments()) {
            commentList.add(new MyPageCommentInfo(comment.getWebtoon().getName(), comment.getContents(), comment.getPostTime()));
        }

        return new MyPageResponseDto(findedMember.getNickname(),
                                     findedMember.getTitle(),
                                     commentList);
    }
}
