package com.company.dao;

import org.springframework.beans.factory.annotation.Autowired;
import com.company.entities.Candidate;
import com.company.entities.Contact;

import javax.persistence.EntityManager;

/**
 * Class implements ContactDAO.
 * Here main application logic for working with entity Contact from MainController. This DAO working with Hibernate, if something bad with
 * connection you can check AppConfig and persistence.xml. Candidate and Contact relationship: One to Many.
 * Exception proceed here, not throws to MainController.
 *
 *
 * Created by tymchenkoivan on 30.05.2015.
 */
public class ContactDAOImpl implements ContactDAO {

    @Autowired
    private EntityManager entityManager;

    /**
     * Creates new Contact and persist it into DB.
     * During working with BD creates Transaction, if there arise Exception situation ==> Transaction().rollback().
     * If one of arguments is null, or empty("") Candidate don't creates, and method ==> return
     *
     * @param candidate if null ==> return.
     * @param description if null or "" ==> return.
     * @param cont if null or "" ==> return.
     */
    @Override
    public void add(Candidate candidate, String description, String cont) {
        try {
            if (candidate == null || description == null || cont == null) {
                return;
            }
            if ("".equals(description) || "".equals(cont)) {
                return;
            }
            Contact contact = new Contact(candidate, description, cont);
            entityManager.getTransaction().begin();
            entityManager.persist(contact);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
        }
    }
}
