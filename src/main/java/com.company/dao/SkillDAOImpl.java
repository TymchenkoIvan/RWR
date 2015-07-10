package com.company.dao;

import com.company.entities.Candidate;
import com.company.entities.Skill;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

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

    private static final Logger logger = Logger.getLogger(SkillDAOImpl.class);

    /**
     * Creates new Skill and persist it into DB.
     * If one of arguments is null, or empty("") Candidate don't creates, and method will return
     * Also skill.rate must be from 0 to 10, if not will return
     *
     * @param candidate String
     * @param description String
     * @param rate String from "0" to "10"
     */
    @Override
    public void add(Candidate candidate, String description, String rate) {
        if(candidate == null || description == null || rate == null){
            return;
        }
        if("".equals(description) || "".equals(rate)){
            return;
        }
        if(Integer.parseInt(rate)<0 || Integer.parseInt(rate)>10){
            return;
        }
        logger.info("tries to create new skill for "+candidate.getId());
        Skill skill = new Skill(candidate, description, Integer.parseInt(rate));
        entityManager.getTransaction().begin();
        entityManager.persist(skill);
        entityManager.getTransaction().commit();
        logger.info("new skill added");
    }
}
