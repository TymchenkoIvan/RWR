package com.company.comparators;

import com.company.entities.Candidate;

import java.util.Comparator;


public class CandidateDataComparator implements Comparator<Candidate> {

    /**
     * Method compares two Candidates and returns -1 or 1. It can't return 0, because candidate.id ia autowired.
     * At first candidates compares by getInterviewDate(). If dates are equals they will compare bi id. Id ia always different.
     * This Comparator is default.
     *
     * @param first Candidate
     * @param second Candidate
     * @return int -1 or 1
     */
    @Override
    public int compare(Candidate first, Candidate second) {
        if(first.getInterviewDate().equals(second.getInterviewDate())){
            return first.getId()- second.getId();
        }
        return first.getInterviewDate().compareTo(second.getInterviewDate());
    }
}