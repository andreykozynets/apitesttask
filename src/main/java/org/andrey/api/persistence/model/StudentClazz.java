package org.andrey.api.persistence.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="student_class")
public class StudentClazz {

    @Id
    @GeneratedValue(strategy= GenerationType.TABLE)
    private Long id;

    @NotNull
    private Long studentId;

    @NotNull
    private Long clazzCode;

    public Long getId() {
        return id;
    }

    public StudentClazz() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getClazzCode() {
        return clazzCode;
    }

    public void setClazzCode(Long clazzCode) {
        this.clazzCode = clazzCode;
    }
}
