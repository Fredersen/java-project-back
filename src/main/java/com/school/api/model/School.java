package com.school.api.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    private String city;
    
    private String type;


    @OneToMany(mappedBy = "school", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Class> classes = new ArrayList<>();
    
    public School(String name, String city, String type) {
        this.name = name;
        this.city = city;
        this.type = type;
    }
    
    public School() {
        
    }
    
    public Long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public void setClasses(List<Class> classes) {
        this.classes = classes;
    }
}
