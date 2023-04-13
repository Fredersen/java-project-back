package com.school.api.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @ManyToOne
    @JoinColumn(name = "class_id")
    @JsonIgnore
    private Class classRoom;

    public Student(String firstName, String lastName, Class classRoom) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.classRoom = classRoom;
    }

    public Student() {
    }

    public Long getId() {
        return id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setClassRoom(Class classRoom) {
        this.classRoom = classRoom;
    }

    public Class getClassRoom() {
        return this.classRoom;
    }

    @JsonProperty("classId")
    public Long getClassId() {
        return this.classRoom.getId();
    }
}
