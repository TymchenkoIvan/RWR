package com.company.comparators;

import com.company.entities.Candidate;

import java.util.Comparator;

public class CandidateNameComparator  implements Comparator<Candidate> {

    /**
     * Method compares two Candidates and returns -1 or 1. It can't return 0, because candidate.id ia autowired.
     * At first candidates compares by getLastName(). If they are equals they will compare bi getFirstName(),
     * finally they can be compared bi id. Id ia always different.
     *
     * @param first Candidate
     * @param second Candidate
     * @return int -1 or 1
     */
    @Override
    public int compare(Candidate first, Candidate second) {
        if(first.getLastName().equals(second.getLastName())){
            if(first.getFirstName().compareToIgnoreCase(second.getFirstName()) == 0){
                return first.getId()- second.getId();
            }
            else{
                return first.getFirstName().compareToIgnoreCase(second.getFirstName());
            }
        }
        return first.getLastName().compareToIgnoreCase(second.getLastName());
    }
}