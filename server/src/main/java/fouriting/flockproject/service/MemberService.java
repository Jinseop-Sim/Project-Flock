package fouriting.flockproject.service;

import fouriting.flockproject.domain.Member;
import fouriting.flockproject.domain.dto.response.session.UserSessionDto;
import fouriting.flockproject.domain.dto.response.infoClass.MyPageCommentInfo;
import fouriting.flockproject.domain.dto.response.MyPageResponseDto;
import fouriting.flockproject.repository.CommentRepository;
import fouriting.flockproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    public MyPageResponseDto showMyPage(UserSessionDto userSessionDto) {
        Member findedMember = memberRepository.findByLoginId(userSessionDto.getLoginId()).orElseThrow();
        // 현재 요청을 보낸 사람의 member id를 딴다.

        List<MyPageCommentInfo> commentList = commentRepository.findByMember(findedMember).stream()
                                            .map(MyPageCommentInfo::new).collect(Collectors.toList());

        return new MyPageResponseDto(findedMember, commentList);
    }

    public String logout(HttpSession session){
        session.invalidate();
        return "로그아웃 되었습니다.";
    }
}
