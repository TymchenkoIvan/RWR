package com.company.dao;

import com.company.entities.Candidate;
import com.company.exception.MyApplicationException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.company.config.MainController.MAX_CANDIDATES_ON_PAGE;


/**
 * Class implements CandidateDAO.
 * Here main application logic for working with entity Candidate from MainController. This DAO working with Hibernate, if something bad with
 * connection you can check AppConfig and persistence.xml.
 *
 * Candidate and Contact relationship: One to Many.
 * Candidate and Skill relationship: One to Many.
 *
 * Created by tymchenkoivan on 30.05.2015.
 */
public class CandidateDAOImpl implements CandidateDAO {

    @Autowired
    private EntityManager entityManager;


    /**
     * Returns Candidate by id, using entityManager
     *
     * @param id int
     * @return Candidate
     */
    @Override
    public Candidate getById(int id) {
        return entityManager.find(Candidate.class, id);
    }


    /**
     * Creates and return new Candidate and persist it into DB.
     *
     * If one of arguments is null, or empty("") Candidate don't creates, and method will return
     * Also format.parse(interviewDate) can throw ParseException.
     * list will be updated.
     *
     * @param firstName String
     * @param lastName String
     * @param interviewDate String that must be converted to Date.
     * @return Candidate or null
     */
    @Override
    public Candidate addAndGet(String firstName, String lastName, String interviewDate) throws MyApplicationException {
        Candidate candidate = null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(interviewDate);
            candidate = new Candidate(firstName, lastName, date);
            entityManager.getTransaction().begin();
            entityManager.persist(candidate);
            entityManager.getTransaction().commit();
        } catch (ParseException e) {
            throw new MyApplicationException("Can not add candidate");
        }
        return candidate;
    }

    /**
     * Deletes Candidate from DB that found by id. Also will be deleted all entities that aggregated and mapped by
     * Candidate(Skill and Contact), because cascade = CascadeType.ALL.
     * list will be updated.
     *
     * @param id int Candidate.id
     */
    @Override
    public void delete(int id) throws MyApplicationException {
        try {
            entityManager.getTransaction().begin();
            Candidate candidate = entityManager.find(Candidate.class, id);
            entityManager.remove(candidate);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            throw new MyApplicationException("Can not delete user");
        }
    }


    /**
     * Returns candidates quantity in database
     *
     * @return int
     */
    @Override
    public int getCandidatesCount() {
        Query query = entityManager.createNativeQuery("SELECT count(*) FROM Candidates");
        return ((BigInteger) query.getSingleResult()).intValue();
    }


    /**
     * Returns candidates quantity in database that match incoming pattern.
     * Method searching matches into candidate names.
     *
     * @param pattern String
     * @return int
     */
    @Override
    public int getCandidatesCount(String pattern) {
        Query query = entityManager.createNativeQuery(
                "SELECT count(*) " +
                        "FROM candidates " +
                        "WHERE (locate(lower('"+pattern+"'),lower(first_name))>0)" +
                        "OR (locate(lower('"+pattern+"'),lower(last_name))>0)");
        return ((BigInteger) query.getSingleResult()).intValue();
    }


    /**
     * Returns one page from sorted database. Database sorts by candidate.interview_date
     *
     * @param page int
     * @return List <Candidate>
     */
    @Override
    public List<Candidate> sortedByDate(int page) {
        Query query = entityManager.createNativeQuery(
                "SElECT * " +
                        "FROM candidates " +
                        "ORDER BY interview_date LIMIT "+ (page-1)*MAX_CANDIDATES_ON_PAGE+","+MAX_CANDIDATES_ON_PAGE,
                Candidate.class);
        return (ArrayList)query.getResultList();
    }


    /**
     * Returns one page from sorted database. Database sorts by candidate.last_name
     *
     * @param page int
     * @return List <Candidate>
     */
    @Override
    public List<Candidate> sortedByName(int page) {
        Query query = entityManager.createNativeQuery(
                "SElECT * " +
                        "FROM candidates " +
                        "ORDER BY last_name LIMIT " + (page-1)*MAX_CANDIDATES_ON_PAGE + "," + MAX_CANDIDATES_ON_PAGE,
                Candidate.class);
        return (ArrayList)query.getResultList();
    }


    /**
     * Returns one page from sorted database.
     * Method searching matches into candidate names using incoming pattern.
     *
     * @param pattern String
     * @param page int
     * @return List <Candidate>
     */
    @Override
    public List<Candidate> sortedByPattern(String pattern, int page) {
        Query query = entityManager.createNativeQuery(
                "SELECT * " +
                        "FROM candidates " +
                        "WHERE (locate(lower('"+pattern+"'),lower(first_name))>0)" +
                        "OR (locate(lower('"+pattern+"'),lower(last_name))>0)" +
                        "ORDER BY interview_date LIMIT "+ (page-1)*MAX_CANDIDATES_ON_PAGE+","+MAX_CANDIDATES_ON_PAGE, Candidate.class);
        return (ArrayList)query.getResultList();
    }
}