package com.company.dao;

import org.springframework.beans.factory.annotation.Autowired;
import com.company.entities.Candidate;
import com.company.entities.Contact;

import javax.persistence.EntityManager;

/**
 * Created by tymchenkoivan on 30.05.2015.
 */
public class ContactDAOImpl implements ContactDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public void add(Candidate candidate, String description, String cont) {
        try {
            if(candidate == null || description == null || cont == null){
                return;
            }
            if("".equals(description) || "".equals(cont)){
                return;
            }
            Contact contact = new Contact(candidate, description, cont);
            entityManager.getTransaction().begin();
            entityManager.persist(contact);
            entityManager.getTransaction().commit();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
        }
    }
}
