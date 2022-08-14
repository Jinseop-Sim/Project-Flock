package fouriting.flockproject.domain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime postTime;
    @Lob
    private String contents;

    @Builder
    public Comment(Member member, Webtoon webtoon, String contents) {
        this.member = member;
        this.webtoon = webtoon;
        this.contents = contents;
    }

    public void addToMember(Member member){
        this.member = member;
        member.getMyComments().add(this);
    }
}