package fouriting.flockproject.repository;

import fouriting.flockproject.domain.Member;
import fouriting.flockproject.domain.StarLike;
import fouriting.flockproject.domain.Webtoon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StarRepository {
    private final EntityManager em;

    public void save(StarLike starLike){
        em.persist(starLike);
    }

    public Optional<StarLike> findByMemberId(Long id){
        List findedMember = em.createQuery("SELECT sl FROM StarLike sl " +
                        "join fetch sl.webtoon WHERE sl.member.id =: id")
                .setParameter("id", id)
                .getResultList();

        return findedMember.stream().findAny();
    }
}
