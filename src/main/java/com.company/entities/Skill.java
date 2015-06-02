package com.company.entities;

import javax.persistence.*;

/**
 * Created by tymchenkoivan on 25.05.2015.
 */
@Entity
@Table(name = "skills")
public class Skill implements Comparable<Skill>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_candidate")
    private Candidate candidate;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int rate;

    public Skill() {
    }

    public Skill(String description, int rate) {
        this.description = description;
        this.rate = rate;
    }

    public Skill(Candidate candidate, String description, int rate) {
        this.setCandidate(candidate);
        this.description = description;
        this.rate = rate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
        if (!candidate.getSkills().contains(this)){
            candidate.getSkills().add(this);
        }
    }

    public Candidate getCandidate() {
        return candidate;
    }

    @Override
    public String toString() {
        return description + " ("+rate+")";
    }

    @Override
    public int compareTo(Skill o) {
        return o.rate - this.rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
