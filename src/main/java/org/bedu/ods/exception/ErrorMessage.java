package org.bedu.ods.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import java.util.*;

public class ErrorMessage {
    private final int statusCode;
    private final Date timestamp;
    private final String message;
    private final List<String> constraints;
    private final String uri;

    public ErrorMessage(int statusCode, Date timestamp, ConstraintViolationException ex, String description) {
        this.constraints = this.buildValidationErrors(ex);
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = "Error en en las validaciones de los datos";
        this.uri = description;
    }

    public ErrorMessage(int statusCode, Date timestamp, String message, String description) {

        HashMap<String,Object> msgMap = new HashMap<>();
        msgMap.put("Class", "ErrorMessage");

        this.constraints = null;
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = message;
        this.uri = description;

    }

    public int getStatusCode() {
        return statusCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getUri() {
        return uri;
    }

    public List<String> getConstraints() { return constraints; }

    private List<String> buildValidationErrors(ConstraintViolationException ex)
    {
        List<String> errors = new ArrayList<>();
            for (
        ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " <<" + violation.getPropertyPath() + ">>: "
                    + violation.getMessage());
        }
        return errors;
    }
}
