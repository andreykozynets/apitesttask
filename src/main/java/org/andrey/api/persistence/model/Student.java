package org.andrey.api.persistence.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="student")
public class Student {

    @Id
    @GeneratedValue(strategy= GenerationType.TABLE)
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    public Long getId() {
        return id;
    }

    public Student() {
    }

    public Student(Long id) {
        this.id = id;
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
