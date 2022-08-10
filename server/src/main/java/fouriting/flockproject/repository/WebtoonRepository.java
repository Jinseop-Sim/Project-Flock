package fouriting.flockproject.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class WebtoonRepository {

    @PersistenceContext
    EntityManager em;


}
