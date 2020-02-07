package com.lunatech.forms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is a data holder that is used to store the list of errors (or none) when an HTTP form was submitted to the server.
 */
public class FormFieldWithErrors {
    private List<FormError> errors;

    public class FormError {
        private String fieldName;
        private String errorMsg;

        public FormError(String fieldName, String errorMsg) {
            this.fieldName = fieldName;
            this.errorMsg = errorMsg;
        }

        public String getFieldName() {
            return fieldName;
        }

        public String getMessage() {
            return errorMsg;
        }

    }
    public static FormFieldWithErrors prepareNew() {
        return new FormFieldWithErrors();
    }

    public FormFieldWithErrors() {
        this.errors = Collections.unmodifiableList(Collections.emptyList());
    }

    public FormFieldWithErrors(List<FormError> errors) {
        this.errors = Collections.unmodifiableList(errors);
    }

    public FormFieldWithErrors assertNonEmpty(String fieldName, String fieldValue) {
        if (fieldValue == null) {
            return this.addNewError(fieldName, fieldName + " cannot be empty");
        }
        if (fieldValue.isEmpty()) {
            return this.addNewError(fieldName, fieldName + " cannot be empty");
        }
        return this;
    }

    public FormFieldWithErrors addNewError(String fieldName, String errorMsg) {
        List<FormError> newError = new ArrayList<>(Collections.emptyList());
        newError.addAll(this.errors);
        newError.add(new FormError(fieldName, errorMsg));
        return new FormFieldWithErrors(newError);
    }

    public boolean hasErrors() {
        if(errors==null) return false;
        return !errors.isEmpty();
    }

    public List<FormError> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    public String getErrorMessage() {
        if(errors == null) return "";
        if (errors.isEmpty()) {
            return "";
        } else {
            return errors.stream().map(error -> "field:" + error.getFieldName() + " error:" + error.getMessage()).collect(Collectors.joining());
        }
    }

    @Override
    public String toString() {
        if (hasErrors()) {
            return "FormFieldWithErrors{" +
                    "errors=" + getErrorMessage() +
                    '}';
        } else {
            return "FormFieldWithErrors{}";
        }

    }
}
