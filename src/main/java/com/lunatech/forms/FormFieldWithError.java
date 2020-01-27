package com.lunatech.forms;

public class FormFieldWithError {
    private String fieldName;
    private String errorMsg;

    public FormFieldWithError(String fieldName, String errorMsg) {
        this.fieldName = fieldName;
        this.errorMsg = errorMsg;
    }

    public String getFieldWithError() {
        return fieldName;
    }

    public String getErrorMessage() {
        return errorMsg;
    }
}
