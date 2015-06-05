package com.company.dao;

import com.company.entities.Candidate;

import java.util.List;


public interface CandidateDAO {
    List<Candidate> getList();
    void sortByDate();
    void sortByName();
    void sortByPattern(String pattern);
    Candidate getById(int id);
    Candidate addAndGet(String firstName, String lastName, String interviewDate);
    void delete(int id);
}
