package com.company.dao;

import com.company.entities.Candidate;


public interface ContactDAO {
    String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    void add(Candidate candidate, String description, String contact);
    boolean isMailReal(String mail);
}
