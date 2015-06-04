package com.company.dao;

import org.springframework.beans.factory.annotation.Autowired;
import com.company.entities.Candidate;
import com.company.entities.Skill;

import javax.persistence.EntityManager;
import java.text.ParseException;

/**
 * Class implements SkillDAO.
 * Here main application logic for working with entity Contact from MainController. This DAO working with Hibernate, if something bad with
 * connection you can check AppConfig and persistence.xml. Candidate and Skill relationship: One to Many.
 * Exception proceed here, not throws to MainController.
 *
 * Created by tymchenkoivan on 30.05.2015.
 */
public class SkillDAOImpl implements SkillDAO{

    @Autowired
    private EntityManager entityManager;

    /**
     * Creates new Skill and persist it into DB.
     * During working with BD creates Transaction, if there arise Exception situation ==> Transaction().rollback().
     * If one of arguments is null, or empty("") Candidate don't creates, and method ==> return
     * Also skill.rate must be from 0 to 10, if not ==> return
     *
     * @param candidate if null ==> return.
     * @param description if null or "" ==> return.
     * @param rate if null or "" or 0< rate >10 ==> return.
     */
    @Override
    public void add(Candidate candidate, String description, String rate) {
        try {
            if(candidate == null || description == null || rate == null){
                return;
            }
            if("".equals(description) || "".equals(rate)){
                return;
            }
            if(Integer.parseInt(rate)<0 || Integer.parseInt(rate)>0){
                return;
            }
            Skill skill = new Skill(candidate, description, Integer.parseInt(rate));
            entityManager.getTransaction().begin();
            entityManager.persist(skill);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
        }
    }
}
