package fouriting.flockproject.repository;

import fouriting.flockproject.domain.Comment;
import fouriting.flockproject.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CommentRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Comment comment, Member member){
        comment.addToMember(member);
        em.persist(comment);
    }
}