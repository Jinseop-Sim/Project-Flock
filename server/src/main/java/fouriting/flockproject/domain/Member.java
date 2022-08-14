package fouriting.flockproject.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
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
    @Column(nullable = false, unique = true)
    private String loginId;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private String passwd;
    @Enumerated(EnumType.STRING)
    private Title title;
    @Embedded
    private StarScore myScore;
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> myComments = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Builder
    public Member(String loginId, String nickname, String passwd) {
        this.loginId = loginId;
        this.nickname = nickname;
        this.passwd = passwd;
    }
}