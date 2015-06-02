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
 * Created by tymchenkoivan on 27.05.2015.
 */
public class CandidateDAOImpl implements CandidateDAO {

    private static ArrayList<Candidate> list;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Candidate getById(int id) {
        return entityManager.find(Candidate.class, id);
    }

    @Override
    public List<Candidate> getList() {
        if(list == null){
            sortByDate();
        }
        return list;
    }

    @Override
    public void sortByDate() {
        Query query = entityManager.createQuery("SELECT c FROM Candidate c", Candidate.class);
        list = (ArrayList)(query.getResultList());
        Collections.sort(list, new CandidateDataComparator());
    }

    @Override
    public void sortByName() {
        Query query = entityManager.createQuery("SELECT c FROM Candidate c", Candidate.class);
        list = (ArrayList)(query.getResultList());
        Collections.sort(list, new CandidateNameComparator());
    }

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

    @Override
    public void add(String firstName, String lastName, String interviewDate) {
        try {
            if(firstName == null || lastName == null || interviewDate == null){
                throw new NullPointerException();
            }
            if("".equals(firstName) || "".equals(lastName) || "".equals(interviewDate)){
                return;
            }
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date date = format.parse(interviewDate);
            Candidate candidate = new Candidate(firstName, lastName, date);

            entityManager.getTransaction().begin();
            entityManager.persist(candidate);
            entityManager.getTransaction().commit();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
        }
    }

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