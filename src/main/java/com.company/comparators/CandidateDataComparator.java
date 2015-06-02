package com.company.comparators;

import com.company.entities.Candidate;

import java.util.Comparator;

/**
 *
 */
public class CandidateDataComparator implements Comparator<Candidate> {
    @Override
    public int compare(Candidate first, Candidate second) {
        if(first.getInterviewDate().equals(second.getInterviewDate())){
            return first.getId()- second.getId();
        }
        return first.getInterviewDate().compareTo(second.getInterviewDate());
    }
}
