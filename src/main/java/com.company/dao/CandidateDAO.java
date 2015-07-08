package com.company.dao;

import com.company.entities.Candidate;
import com.company.exception.MyApplicationException;

import java.util.List;


public interface CandidateDAO {

    Candidate getById(int id);
    Candidate addAndGet(String firstName, String lastName, String interviewDate) throws MyApplicationException;
    void delete(int id) throws MyApplicationException;
    int getCandidatesCount();
    int getCandidatesCount(String pattern);
    List<Candidate> sortedByDate(int page);
    List<Candidate> sortedByName(int page);
    List<Candidate> sortedByPattern(String pattern, int page);
}
