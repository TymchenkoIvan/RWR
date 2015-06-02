package com.company.dao;

import org.springframework.beans.factory.annotation.Autowired;
import com.company.entities.Candidate;
import com.company.entities.Skill;

import javax.persistence.EntityManager;
import java.text.ParseException;

/**
 * Created by tymchenkoivan on 30.05.2015.
 */
public class SkillDAOImpl implements SkillDAO{

    @Autowired
    private EntityManager entityManager;

    @Override
    public void add(Candidate candidate, String description, String rate) {
        try {
            if(candidate == null || description == null || rate == null){
                throw new NullPointerException();
            }
            if("".equals(description) || "".equals(rate)){
                return;
            }
            Skill skill = new Skill(candidate, description, Integer.parseInt(rate));
            entityManager.getTransaction().begin();
            entityManager.persist(skill);
            entityManager.getTransaction().commit();
        } catch (NullPointerException | NumberFormatException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
        }
    }
}
