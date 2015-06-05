package com.company.dao;

import com.company.entities.Candidate;


public interface ContactDAO {
    void add(Candidate candidate, String description, String contact);
}
