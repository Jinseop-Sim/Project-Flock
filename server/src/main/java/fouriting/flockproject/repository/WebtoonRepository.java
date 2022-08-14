package fouriting.flockproject.repository;

import fouriting.flockproject.domain.Genre;
import fouriting.flockproject.domain.Webtoon;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class WebtoonRepository {
    @PersistenceContext
    EntityManager em;

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

    public Optional<Webtoon> findById(Long id){
        List webtoons = em.createQuery("SELECT w FROM Webtoon w WHERE w.id =: id")
                .setParameter("id", id)
                .getResultList();

        return webtoons.stream().findAny();
    }
}