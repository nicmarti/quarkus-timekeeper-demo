package com.lunatech.forms;

import io.quarkus.qute.RawString;
import io.quarkus.qute.TemplateExtension;

import java.util.stream.Collectors;

/**
 * This is a simple DTO to store why the form was not validated.
 * We can use this with a 400 Bad Request and re-render the template.
 * I tried to mimic Play2 Java Form in a very simplistic way.
 * <p>
 * Other ideas : we could specify the list of fields with errors, and the reason for each field.
 * <p>
 * But at the end, this would be more or less like Play2 Form and FormFactory with a ValidationError.
 * Maybe one day.
 *
 * @author Nicolas Martignole
 */
@TemplateExtension
public class Form {
    private final String actionURI ;
    private final FormFieldWithErrors formFieldWithErrors;

    public Form(String actionURI) {
        this.actionURI = actionURI;
        this.formFieldWithErrors = new FormFieldWithErrors();
    }

    public Form(String actionURI,FormFieldWithErrors formFieldWithErrors) {
        this.actionURI = actionURI;
        this.formFieldWithErrors = formFieldWithErrors;
    }

    public String getActionURI() {
        return actionURI;
    }

    /**
     * I had to write this because we cannot test if the formErrors instance is defined or not in Qute.
     * <p>
     * I tried this :
     * <p>
     * #{if formErrors}
     * <div class="alert alert-danger">
     * formErrors.reason
     * </div>
     * {/if}
     * <p>
     * But it will print "NOT_FOUND" in the HTML page
     * <p>
     * I also tried to use the elvis operator. But the div with alert would be print (and I don't want to)
     * <div class="alert alert-danger">
     * #{formErrors.reason ?: ''}
     * </div>
     *
     * @return
     */
    public io.quarkus.qute.RawString getRenderIfErrors() {
        if (formFieldWithErrors != null && formFieldWithErrors.hasErrors()) {
            String listOfErrors = formFieldWithErrors.getErrors().stream().map(error -> "field: " + error.getFieldName() + " error: " + error.getMessage() + "<br>").collect(Collectors.joining());
            String r = "<div class=\"alert alert-danger\">" + listOfErrors + "</div>";
            return new RawString(r);
        } else {
            return new RawString("");
        }
    }

    public boolean hasErrors(){
        return formFieldWithErrors.hasErrors();
    }


}
