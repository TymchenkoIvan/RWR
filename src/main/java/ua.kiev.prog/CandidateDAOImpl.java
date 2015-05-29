package ua.kiev.prog;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by tymchenkoivan on 27.05.2015.
 */
public class CandidateDAOImpl implements CandidateDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Candidate getById(int id) {
        return entityManager.find(Candidate.class, id);
    }

    @Override
    public Set<Candidate> sortedByDate() {
        Set<Candidate> candidates = new TreeSet<>(new CandidateDataComparator());
        Query query = entityManager.createQuery("SELECT c FROM Candidate c", Candidate.class);
        candidates.addAll(query.getResultList());
        return candidates;
    }

    @Override
    public Set<Candidate> sortedByName() {
        Set<Candidate> candidates = new TreeSet<>(new CandidateNameComparator());
        Query query = entityManager.createQuery("SELECT c FROM Candidate c", Candidate.class);
        candidates.addAll(query.getResultList());
        return candidates;
    }

    @Override
    public List<Candidate> list(String pattern) {
        return null;
    }


    @Override
    public void add(Candidate candidate) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(candidate);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
        }
    }

    @Override
    public void addSkill(Skill skill) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(skill);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
        }
    }

    @Override
    public void addContact(Contact contact) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(contact);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            entityManager.getTransaction().begin();
            Candidate candidate = entityManager.find(Candidate.class, id);
            entityManager.remove(candidate);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
        }
    }
}
