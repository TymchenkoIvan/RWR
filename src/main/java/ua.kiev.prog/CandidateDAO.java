package ua.kiev.prog;

import java.util.List;
import java.util.Set;

/**
 * Created by tymchenkoivan on 27.05.2015.
 */
public interface CandidateDAO {
    Candidate getById(int id);
    Set<Candidate> sortedByDate();
    Set<Candidate> sortedByName();
    List<Candidate> list(String pattern);
    void add(Candidate candidate);
    void addSkill(Skill skill);
    void addContact(Contact contact);
    void delete(int id);
}
