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

    public Comment findById(Long id){
        return em.createQuery("SELECT c FROM Comment c WHERE c.id=:id", Comment.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public List<Comment> findByMember(Member member){
        return em.createQuery("SELECT c FROM Comment c join fetch c.webtoon WHERE c.member =: member", Comment.class)
                .setParameter("member", member)
                .getResultList();
    }

    public List<Comment> findByPostId(Long postId){
        return em.createQuery("SELECT c FROM Comment c WHERE c.webtoon.id =: postId " +
                                      "ORDER BY c.parentComment.id ASC, c.postTime ASC ", Comment.class)
                .setParameter("postId", postId)
                .getResultList();
    }
}