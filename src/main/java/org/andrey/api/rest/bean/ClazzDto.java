package org.andrey.api.rest.bean;

import org.andrey.api.persistence.model.Clazz;

public class ClazzDto {

    private Long code;

    private String title;

    private String description;

    public ClazzDto() {
    }

    public ClazzDto(Clazz clazz) {

        this.setTitle(clazz.getTitle());
        this.setDescription(clazz.getDescription());
        this.setCode(clazz.getCode());
    }

    public Long getCode() {
        return code;
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
