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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Skill)) return false;

        Skill skill = (Skill) o;

        if (id != skill.id) return false;
        if (rate != skill.rate) return false;
        if (candidate != null ? !candidate.equals(skill.candidate) : skill.candidate != null) return false;
        if (description != null ? !description.equals(skill.description) : skill.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (candidate != null ? candidate.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + rate;
        return result;
    }
}
