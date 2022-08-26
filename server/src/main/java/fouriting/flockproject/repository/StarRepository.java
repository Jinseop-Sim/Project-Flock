package fouriting.flockproject.repository;

import fouriting.flockproject.domain.StarLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StarRepository {
    private final EntityManager em;

    public void save(StarLike starLike){
        em.persist(starLike);
    }

    public Optional<StarLike> findByMemberId(Long memberId, Long webtoonId){
        List<StarLike> findedStar = em.createQuery("SELECT sl FROM StarLike sl " +
                        "join fetch sl.webtoon WHERE sl.member.id =: memberId AND sl.webtoon.id =: webtoonId", StarLike.class)
                .setParameter("memberId", memberId)
                .setParameter("webtoonId", webtoonId)
                .getResultList();

        return findedStar.stream().findAny();
    }
}
