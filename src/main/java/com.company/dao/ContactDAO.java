package com.company.dao;

import com.company.entities.Candidate;

/**
 * Created by tymchenkoivan on 30.05.2015.
 */
public interface ContactDAO {
    void add(Candidate candidate, String description, String contact);
}
