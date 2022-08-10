package fouriting.flockproject.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @Column(name = "MEMBER_ID") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String loginId;
    private String nickname;
    private String passwd;
    @Enumerated(EnumType.STRING)
    private Title title;
    @Embedded
    private StarScore myScore;
    @OneToMany(mappedBy = "member")
    private List<Comment> myComments;

    @Builder
    public Member(String loginId, String nickname, String passwd) {
        this.loginId = loginId;
        this.nickname = nickname;
        this.passwd = passwd;
    }
}
