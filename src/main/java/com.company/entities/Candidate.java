package com.company.entities;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name = "candidates")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "create_date", nullable = false)
    private Date createDate;

    @Column(name = "interview_date", nullable = false)
    private Date interviewDate;

    @OneToMany(mappedBy="candidate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Skill> skills = new ArrayList<>();

    @OneToMany(mappedBy="candidate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Contact> contacts = new ArrayList<>();

    public Candidate() {
    }

    public Candidate(String firstName, String lastName, Date interviewDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        createDate = new Date();
        this.interviewDate = interviewDate;
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
        if (contact.getCandidate() != this){
            contact.setCandidate(this);
        }
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public List<Contact> getSortedContacts() {
        Collections.sort(contacts);
        return contacts;
    }

    public void addSkill(Skill skill) {
        skills.add(skill);
        if (skill.getCandidate() != this){
            skill.setCandidate(this);
        }
    }

    public List<Skill> getSkills() {
        return skills;
    }

    /**
     * Returns List<Skill> sorted by skill.rate. Biggest value at first and so on
     *
     * @return List <Skill>
     */
    public List<Skill> getSortedSkills() {
        Collections.sort(skills);
        return skills;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getInterviewDate() {
        return interviewDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method returns usefull info about Candidate, and contains lastName, firstName, all Candidates Skills and Contacts.
     *
     * @return String
     */
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();

        builder.append(lastName+" ");
        builder.append(firstName+" ");
        for(Skill skill: getSkills()){
            builder.append(skill.getDescription()+" ");
        }

        for(Contact contact: getContacts()){
            builder.append(contact.getContact()+" ");
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Candidate)) return false;

        Candidate candidate = (Candidate) o;

        if (id != candidate.id) return false;
        if (contacts != null ? !contacts.equals(candidate.contacts) : candidate.contacts != null) return false;
        if (createDate != null ? !createDate.equals(candidate.createDate) : candidate.createDate != null) return false;
        if (firstName != null ? !firstName.equals(candidate.firstName) : candidate.firstName != null) return false;
        if (interviewDate != null ? !interviewDate.equals(candidate.interviewDate) : candidate.interviewDate != null)
            return false;
        if (lastName != null ? !lastName.equals(candidate.lastName) : candidate.lastName != null) return false;
        if (skills != null ? !skills.equals(candidate.skills) : candidate.skills != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (interviewDate != null ? interviewDate.hashCode() : 0);
        result = 31 * result + (skills != null ? skills.hashCode() : 0);
        result = 31 * result + (contacts != null ? contacts.hashCode() : 0);
        return result;
    }
}