package fouriting.flockproject.service;

import fouriting.flockproject.domain.Comment;
import fouriting.flockproject.domain.Member;
import fouriting.flockproject.domain.dto.response.infoClass.MyPageCommentInfo;
import fouriting.flockproject.domain.dto.response.MyPageResponseDto;
import fouriting.flockproject.repository.CommentRepository;
import fouriting.flockproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    public MyPageResponseDto showMyPage(HttpServletRequest request) {
        List<MyPageCommentInfo> commentList = new ArrayList<>();
        // 현재 요청을 보낸 사람의 member id를 딴다.
        Member findedMember = getCurrentMember(request);
        List<Comment> findedComments = commentRepository.findByMember(findedMember);
        for (Comment comment : findedComments) {
            commentList.add(new MyPageCommentInfo(comment.getWebtoon().getName(), comment.getContents(), comment.getPostTime()));
        }

        return new MyPageResponseDto(findedMember.getNickname(),
                                     findedMember.getTitle(),
                                     commentList);
    }

    public void logout(HttpServletRequest request){
        HttpSession currentSession = request.getSession();
        if(currentSession != null)
            currentSession.invalidate();
    }

    public Member getCurrentMember(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        return (Member)session.getAttribute("loginUser");
    }
}
