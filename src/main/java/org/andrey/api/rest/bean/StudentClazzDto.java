package org.andrey.api.rest.bean;

import org.andrey.api.persistence.model.StudentClazz;

public class StudentClazzDto {

    private Long id;

    private Long clazzCode;

    private Long studentId;

    public StudentClazzDto() {
    }

    public StudentClazzDto(StudentClazz studentClazz) {

        this.setId(studentClazz.getId());
        this.setStudentId(studentClazz.getStudentId());
        this.setClazzCode(studentClazz.getClazzCode());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClazzCode() {
        return clazzCode;
    }

    public void setClazzCode(Long clazzCode) {
        this.clazzCode = clazzCode;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
