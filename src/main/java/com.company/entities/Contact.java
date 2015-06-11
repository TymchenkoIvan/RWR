package com.company.entities;

import javax.persistence.*;

/**
 * Created by tymchenkoivan on 25.05.2015.
 */
@Entity
@Table(name = "contacts")
public class Contact implements Comparable<Contact>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_candidate")
    private Candidate candidate;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String contact;

    public Contact() {
    }

    public Contact(String contact, String description) {
        this.contact = contact;
        this.description = description;
    }

    public Contact(Candidate candidate, String description, String contact) {
        this.setCandidate(candidate);
        this.description = description;
        this.contact = contact;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
        if ( ! candidate.getContacts().contains(this)){
            candidate.getContacts().add(this);
        }
    }

    @Override
    public int compareTo(Contact o) {
        return this.id - o.id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;

        Contact contact1 = (Contact) o;

        if (id != contact1.id) return false;
        if (candidate != null ? !candidate.equals(contact1.candidate) : contact1.candidate != null) return false;
        if (contact != null ? !contact.equals(contact1.contact) : contact1.contact != null) return false;
        if (description != null ? !description.equals(contact1.description) : contact1.description != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (candidate != null ? candidate.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        return result;
    }
}
