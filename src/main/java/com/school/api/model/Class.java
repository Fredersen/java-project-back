package com.school.api.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String level;

    @ManyToOne
    @JoinColumn(name = "school_id")
    @JsonIgnore
    private School school;

    @OneToMany(mappedBy = "classRoom", cascade = CascadeType.REMOVE)
    private List<Student> students = new ArrayList<>();

    public Class(String name, String level, School school) {
        this.name = name;
        this.level = level;
        this.school = school;
    }

    public Class() {
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return this.level;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public School getSchool() {
        return this.school;
    }

    @JsonProperty("schoolId")
    public Long getSchoolId() {
        return (school != null) ? school.getId() : null;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
