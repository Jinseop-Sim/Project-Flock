package fouriting.flockproject.repository;

import fouriting.flockproject.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    @PersistenceContext
    EntityManager em;

    public Member save(Member member){
        em.persist(member);
        return member;
    }

    public Optional<Member> findByLoginId(String loginId) {
        List members = em.createQuery("SELECT m FROM Member m WHERE m.loginId =:loginId")
                .setParameter("loginId", loginId)
                .getResultList();
        return members.stream().findAny();
    }

    public Optional<Member> findById(Long id) {
        List members = em.createQuery("SELECT m FROM Member m WHERE m.id =:id")
                .setParameter("id", id)
                .getResultList();
        return members.stream().findAny();
    }
}