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
            builder.append(contact.getDescription()+" ");
        }
        return builder.toString();
    }
}