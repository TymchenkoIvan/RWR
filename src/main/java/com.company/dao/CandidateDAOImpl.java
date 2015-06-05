package com.company.dao;

import org.springframework.beans.factory.annotation.Autowired;
import com.company.entities.Candidate;
import com.company.comparators.CandidateDataComparator;
import com.company.comparators.CandidateNameComparator;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Class implements CandidateDAO.
 * Here main application logic for working with entity Candidate from MainController. This DAO working with Hibernate, if something bad with
 * connection you can check AppConfig and persistence.xml.
 *
 * Candidate and Contact relationship: One to Many.
 * Candidate and Skill relationship: One to Many.
 * Exception proceed here, not throws to MainController.
 *
 * Methods uses static ArrayList<Candidate> list, update and returns it's link to MainController.
 *
 *
 * Created by tymchenkoivan on 30.05.2015.
 */
public class CandidateDAOImpl implements CandidateDAO {

    private static ArrayList<Candidate> list;

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
     * Returns static list from cash. It can be sorted by candidate.interviewDate, candidate.names and String. For more details
     * look methods sortByDate(), sortByName(), sortByPattern() and package com.company.comparators.
     * If list is not initialized, method create list using method sortByDate().
     *
     * @return List <Candidate>
     */
    @Override
    public List<Candidate> getList() {
        if(list == null){
            sortByDate();
        }
        return list;
    }


    /**
     * Method sorts and update static list using com.company.comparators.CandidateDataComparator. list updates from BD using
     * entityManager. SortByDate() is default sorting.
     *
     */
    @Override
    public void sortByDate() {
        Query query = entityManager.createQuery("SELECT c FROM Candidate c", Candidate.class);
        list = (ArrayList)(query.getResultList());
        Collections.sort(list, new CandidateDataComparator());
    }


    /**
     * Method sorts and update static list using com.company.comparators.CandidateNameComparator. list updates from BD using
     * entityManager. SortByName() is not default sorting.
     *
     */
    @Override
    public void sortByName() {
        Query query = entityManager.createQuery("SELECT c FROM Candidate c", Candidate.class);
        list = (ArrayList)(query.getResultList());
        Collections.sort(list, new CandidateNameComparator());
    }


    /**
     * Method sorts and update static list using String pattern. If (pattern == 0 && "".equals(pattern)) method will
     * sort list by default sorting, using sortByDate().
     *
     * For difficult search user need to separate argument with " ". If argument ok, method will parse argument using .split(" ").
     * After that it takes all Strings from array, and try find it in Candidate.toString() using toLowerCase().contains().
     * Candidates takes from BD using entityManager, and only if Candidate.toString() contains ALL patterns in array Candidate wil be added to list.
     *
     * Candidate.toString() returns info about candidate and contains lastName, firstName, all Candidate Skills and Contacts.
     */
    @Override
    public void sortByPattern(String pattern) {
        if(pattern == null || "".equals(pattern)){
            sortByDate();
        }
        else{
            String[] patternArray = pattern.split(" ");
            Query query = entityManager.createQuery("SELECT c FROM Candidate c", Candidate.class);
            list = (ArrayList)(query.getResultList());

            for(int i=0; i<list.size(); i++){
                String temp = list.get(i).toString();
                boolean flag = true;
                for(String s: patternArray){
                    if(!temp.toLowerCase().contains(s.toLowerCase())){
                        flag = false;
                        break;
                    }
                }
                if(!flag){
                    list.remove(i--);
                }
            }
        }
    }


    /**
     * Creates and return new Candidate and persist it into DB.
     *
     * If one of arguments is null, or empty("") Candidate don't creates, and method will return
     * Also format.parse(interviewDate) can throw ParseException.
     * During working with BD creates Transaction, if there arise Exception situation will be Transaction().rollback().
     *
     * @param firstName String
     * @param lastName String
     * @param interviewDate String that must be converted to Date.
     * @return Candidate or null
     */
    @Override
    public Candidate addAndGet(String firstName, String lastName, String interviewDate) {
        Candidate candidate = null;
        try {
            if(firstName == null || lastName == null || interviewDate == null){
                throw new NullPointerException();
            }
            if("".equals(firstName) || "".equals(lastName) || "".equals(interviewDate)){
                return null;
            }
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date date = format.parse(interviewDate);
            candidate = new Candidate(firstName, lastName, date);

            entityManager.getTransaction().begin();
            entityManager.persist(candidate);
            entityManager.getTransaction().commit();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
        }

        return candidate;
    }

    /**
     * Deletes Candidate from DB that found by id. Also will be deleted all entities that aggregated and mapped by
     * Candidate(Skill and Contact), because cascade = CascadeType.ALL.
     * During working with BD creates Transaction, if there arise Exception situation will be Transaction().rollback().
     *
     * @param id int Candidate.id
     */
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