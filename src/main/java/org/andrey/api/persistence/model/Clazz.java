package org.andrey.api.persistence.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
/*@NamedNativeQuery(
        name="findClassesForStudent",
        query = "SELECT * from class c JOIN student_class sc ON (c.code = sc.clazzCode) WHERE sc.studentId = :studentId"
)*/
@Table(name="class")
public class Clazz {

    @Id
    @GeneratedValue(strategy= GenerationType.TABLE)
    private Long code;

    @NotNull
    private String title;

    private String description;

    public Long getCode() {
        return code;
    }

    public Clazz() {
    }

    public Clazz(Long code) {
        this.code = code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
