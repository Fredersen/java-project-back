package com.school.api.dto;

public class ClassDTO {
    private String name;
    private String level;
    private Long schoolId;

    public ClassDTO() {
    }

    public ClassDTO(String name, String level, Long schoolId) {
        this.name = name;
        this.level = level;
        this.schoolId = schoolId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }
}
