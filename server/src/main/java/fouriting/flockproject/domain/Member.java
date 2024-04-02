package fouriting.flockproject.domain;

import fouriting.flockproject.domain.dto.request.auth.MemberSignUpDto;
import fouriting.flockproject.domain.dto.request.comment.CommentRequestDto;
import fouriting.flockproject.domain.enumClass.Authority;
import fouriting.flockproject.domain.enumClass.Title;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @Column(name = "MEMBER_ID") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    @NotBlank(message = "ID는 필수 값입니다.")
    private String loginId;
    @Column(nullable = false)
    @NotBlank
    private String nickname;
    @Column(nullable = false)
    @NotBlank
    private String password;
    @Enumerated(EnumType.STRING)
    private Title title;

    // Foreign Table
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> myComments = new ArrayList<>();

    // Spring Security
    @Enumerated(EnumType.STRING)
    private Authority authority;

    // Data for title
    private Integer scoreOne;
    private Integer scoreFive;

    public Member(MemberSignUpDto memberSignUpDto) {
        this.loginId = memberSignUpDto.getLoginId();
        this.nickname = memberSignUpDto.getNickname();
        this.password = memberSignUpDto.getPasswd();
        this.authority = Authority.ROLE_USER;
        this.title = Title.새싹;
        this.scoreFive = 0;
        this.scoreOne = 0;
    }
    public static Member toMember(MemberSignUpDto memberSignUpDto){
        return new Member(memberSignUpDto);
    }

    public void encodePassword(String passwd){
        this.password = passwd;
    }

    public void updateTitle(Comment comment){
        this.myComments.add(comment);

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