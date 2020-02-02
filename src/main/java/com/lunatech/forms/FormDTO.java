package com.lunatech.forms;

import java.util.Map;

/**
 * A class that implement this interface can expose which attributes are set in a Form
 */
public interface FormDTO {
    /**
     * Return the current values of a Form, so we can pre-fill the input text (for instance)
     * @return a Map of field name with values or empty.
     */
    Map<String,String> getFieldValues();
}
