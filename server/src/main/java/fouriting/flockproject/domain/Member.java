package fouriting.flockproject.domain;

import fouriting.flockproject.domain.enumClass.Authority;
import fouriting.flockproject.domain.enumClass.Title;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    // Foreign Table
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> myComments = new ArrayList<>();
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StarLike> myStars = new ArrayList<>();

    // Spring Security
    @Enumerated(EnumType.STRING)
    private Authority authority;

    // Data for title
    private Integer scoreOne;
    private Integer scoreFive;

    @Builder
    public Member(String loginId, String nickname, String passwd) {
        this.loginId = loginId;
        this.nickname = nickname;
        this.passwd = passwd;
    }

    public void updateTitle(){
        if(this.getMyComments().size() > 10)
            this.title = Title.박찬호;
        if(this.scoreOne > 10)
            this.title = Title.테러리스트;
        if(this.scoreFive > 20)
            this.title = Title.천사;
    }

    public void addStarOne(){
        this.scoreOne++;
    }
    public void addStarFive(){
        this.scoreFive++;
    }
    public void subStarOne(){
        this.scoreOne--;
    }
    public void subStarFive(){
        this.scoreFive--;
    }
}