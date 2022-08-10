package fouriting.flockproject.domain;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Comment {
    @Id
    @Column(name = "COMMENT_ID")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "WEBTOON_ID")
    private Webtoon webtoon;
    @Embedded
    private StarScore starScore;
    private LocalDateTime postTime;
    @Lob
    private String contents;
}
