package fouriting.flockproject.domain;
import fouriting.flockproject.domain.dto.request.comment.CommentRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WEBTOON_ID")
    private Webtoon webtoon;
    private String author;
    private LocalDateTime postTime;
    @Lob
    private String contents;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_COMMENT_ID")
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> childComments;

    private Comment(CommentRequestDto commentRequestDto, Member member, Webtoon webtoon, Comment comment){
        this.contents = commentRequestDto.getContents();
        this.postTime = LocalDateTime.now();
        this.member = member;
        this.webtoon = webtoon;
        this.author = member.getNickname();
        this.parentComment = comment;
    }

    public static Comment toCommentWithoutParent(CommentRequestDto commentRequestDto, Member member, Webtoon webtoon){
        return new Comment(commentRequestDto, member, webtoon, null);
    }

    public static Comment toCommentWithParent(CommentRequestDto commentRequestDto, Member member, Webtoon webtoon, Comment comment){
        return new Comment(commentRequestDto, member, webtoon, comment);
    }

    public void addChildComment(Comment comment){
        this.childComments.add(comment);
    }
}