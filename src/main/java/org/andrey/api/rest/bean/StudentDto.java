package org.andrey.api.rest.bean;

import org.andrey.api.persistence.model.Student;

public class StudentDto {

    private Long id;

    private String firstName;

    private String lastName;

    public StudentDto() {
    }

    public StudentDto(Student student) {

        this.setFirstName(student.getFirstName());
        this.setLastName(student.getLastName());
        this.setId(student.getId());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
