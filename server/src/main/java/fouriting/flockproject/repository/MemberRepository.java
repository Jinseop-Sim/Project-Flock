package fouriting.flockproject.repository;

import fouriting.flockproject.domain.Member;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {
    @PersistenceContext
    EntityManager em;

    public Member save(Member member){
        em.persist(member);
        return member;
    }

    public Member findById(String loginId){
        Member findedMember = em.find(Member.class, loginId);
        return findedMember;
    }
}
