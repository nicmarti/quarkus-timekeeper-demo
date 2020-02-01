package com.lunatech.forms;

import io.quarkus.qute.RawString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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


    public FormFieldWithErrors() {
        this.errors = Collections.unmodifiableList(Collections.emptyList());
    }

    public FormFieldWithErrors(List<FormError> errors) {
        this.errors = Collections.unmodifiableList(errors);
    }

    public FormFieldWithErrors nonEmpty(String fieldName, String fieldValue) {
        if (fieldValue == null) {
            return this.addNewError(fieldName, fieldName + " cannot be empty");
        }
        if (fieldValue.isEmpty()) {
            return this.addNewError(fieldName, fieldName + " cannot be empty");
        }
        return this;
    }

    public static FormFieldWithErrors prepareNew() {
        return new FormFieldWithErrors();
    }

    public FormFieldWithErrors addNewError(String fieldName, String errorMsg) {
        List<FormError> newError = new ArrayList<>(Collections.emptyList());
        newError.addAll(this.errors);
        newError.add(new FormError(fieldName, errorMsg));
        return new FormFieldWithErrors(newError);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public List<FormError> getErrors() {
        return errors;
    }

    public String getErrorMessage() {
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
