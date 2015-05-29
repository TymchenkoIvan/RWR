package ua.kiev.prog;

import java.util.Comparator;

/**
 * Created by tymchenkoivan on 27.05.2015.
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
