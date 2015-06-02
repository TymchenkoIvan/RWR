package com.company.comparators;

import com.company.entities.Candidate;

import java.util.Comparator;

/**
 * Created by tymchenkoivan on 27.05.2015.
 */
public class CandidateNameComparator  implements Comparator<Candidate> {
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