package com.school.api.dto;

public class StudentDTO {
    private String firstName;
    private String lastName;
    private Long classId;

    public StudentDTO() {
    }

    public StudentDTO(String firstName, String lastName, Long classId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.classId = classId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }
}
