package com.company.dao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.company.entities.Candidate;
import com.company.entities.Contact;

import javax.persistence.EntityManager;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class implements ContactDAO.
 * Here main application logic for working with entity Contact from MainController. This DAO working with Hibernate, if something bad with
 * connection you can check AppConfig and persistence.xml. Candidate and Contact relationship: One to Many.
 *
 *
 * Created by tymchenkoivan on 30.05.2015.
 */
public class ContactDAOImpl implements ContactDAO {

    @Autowired
    private EntityManager entityManager;

    private static final Logger logger = Logger.getLogger(CandidateDAOImpl.class);

    /**
     * Creates new Contact and persist it into DB.
     * If one of arguments is null, or empty("") Candidate don't creates, and method will return
     *
     * @param candidate String
     * @param description String
     * @param cont String
     */
    @Override
    public void add(Candidate candidate, String description, String cont) {
        if (candidate == null || description == null || cont == null) {
            return;
        }
        if ("".equals(description) || "".equals(cont)) {
            return;
        }

        logger.info("tries to create new contact for "+candidate.getId());
        Contact contact = new Contact(candidate, description, cont);
        entityManager.getTransaction().begin();
        entityManager.persist(contact);
        entityManager.getTransaction().commit();
        logger.info("new contact added");
    }

    /**
     * Method checks mail address using regex pattern
     *
     * @param mail String
     * @return boolean
     */
    @Override
    public boolean isMailReal(String mail) {
        logger.info("checks is mail real: "+mail);
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(mail);
        boolean flag = matcher.matches();
        logger.info("mail real: "+flag);
        return flag;
    }
}
