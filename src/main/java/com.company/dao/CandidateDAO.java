package com.company.dao;

import com.company.entities.Candidate;

import java.util.List;

/**
 * Created by tymchenkoivan on 27.05.2015.
 */
public interface CandidateDAO {
    List<Candidate> getList();
    void sortByDate();
    void sortByName();
    void sortByPattern(String pattern);
  //  void add(String firstName, String lastName, String interviewDate);
    Candidate getById(int id);
    Candidate addAndGet(String firstName, String lastName, String interviewDate);
    void delete(int id);
}
