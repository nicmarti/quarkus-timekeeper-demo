package com.lunatech.forms;

import io.quarkus.qute.RawString;

/**
 * This is a simple DTO to store why the form was not validated.
 * We can use this with a 400 Bad Request and re-render the template.
 * I tried to mimic Play2 Java Form in a very simplistic way.
 *
 * Other ideas : we could specify the list of fields with errors, and the reason for each field.
 *
 * But at the end, this would be more or less like Play2 Form and FormFactory with a ValidationError.
 * Maybe one day.
 * @author Nicolas Martignole
 */
public class FormErrors {
    private final String reason;

    public FormErrors(String reason){
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    /**
     * I had to write this because we cannot test if the formErrors instance is defined or not in Qute.
     *
     * I tried this :
     *
     *  #{if formErrors}
     *      <div class="alert alert-danger">
     *      formErrors.reason
     *      </div>
     *  {/if}
     *
     *  But it will print "NOT_FOUND" in the HTML page
     *
     *  I also tried to use the elvis operator. But the div with alert would be print (and I don't want to)
     *  <div class="alert alert-danger">
     *      #{formErrors.reason ?: ''}
     *  </div>
     *
     * @return
     */
    public io.quarkus.qute.RawString getRenderIfErrors() {
        if(reason!=null){
            String r = "<div class=\"alert alert-danger\">" + reason + "</div>";
            return new RawString(r);
        }else{
            return new RawString("");
        }
    }
}
