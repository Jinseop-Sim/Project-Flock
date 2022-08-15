package fouriting.flockproject.repository;

import fouriting.flockproject.domain.Comment;
import fouriting.flockproject.domain.Member;
import fouriting.flockproject.domain.Webtoon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {
    private final EntityManager em;

    public void save(Comment comment){
        em.persist(comment);
    }
}