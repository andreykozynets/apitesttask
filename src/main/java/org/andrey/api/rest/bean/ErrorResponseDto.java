package org.andrey.api.rest.bean;

import java.util.Arrays;
import java.util.List;

public class ErrorResponseDto {

    private Integer status;

    private List<String> errors;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public static ErrorResponseDto build(Integer status, String... messages) {

        ErrorResponseDto errorDTO = new ErrorResponseDto();
        errorDTO.setStatus(status);
        errorDTO.setErrors(Arrays.asList(messages));

        return errorDTO;
    }
}
