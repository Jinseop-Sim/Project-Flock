package fouriting.flockproject.repository;

import fouriting.flockproject.domain.enumClass.Genre;
import fouriting.flockproject.domain.Webtoon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WebtoonRepository {
    private final EntityManager em;

    public List<Webtoon> findByGenre(Genre genre){
        List<Webtoon> webtoons = em.createQuery("SELECT w FROM Webtoon w WHERE w.genre =: genre", Webtoon.class)
                .setParameter("genre", genre)
                .getResultList();

        return webtoons;
    }

    public List<Webtoon> findByName(String name){
        List<Webtoon> webtoons = em.createQuery("SELECT w FROM Webtoon w WHERE w.name LIKE :name", Webtoon.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();

        return webtoons;
    }

    public Webtoon findById(Long id){
        Webtoon webtoons = em.createQuery("SELECT w FROM Webtoon w WHERE w.id =: id", Webtoon.class)
                .setParameter("id", id)
                .getSingleResult();

        return webtoons;
    }
}