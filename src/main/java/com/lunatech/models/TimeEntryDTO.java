package com.lunatech.models;

import com.lunatech.forms.FormDTO;
import com.lunatech.forms.FormFieldWithErrors;
import com.lunatech.forms.Validatable;
import io.vavr.control.Either;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TimeEntryDTO implements Validatable<TimeEntry>, FormDTO {

    @FormParam("description")
    @NotNull
    public String description;

    @FormParam("author")
    @NotNull
    @Email
    public String author;

    @FormParam("duration")
    public String durationAsString;

    @Override
    public String toString() {
        return "TimeEntryDTO{" +
                "description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", durationAsString='" + durationAsString + '\'' +
                '}';
    }

    /**
     * Some of this code should be in the utility forms package and not here.
     *
     * I was thinking to create a valid
     *
     * @return
     */
    @Override
    public Either<FormFieldWithErrors, TimeEntry> valid() {
        FormFieldWithErrors errors = FormFieldWithErrors
                .prepareNew()
                .assertNonEmpty("description", description)
                .assertNonEmpty("author", author);

        if (errors.hasErrors()) {
            return Either.left(errors);
        } else {
            TimeEntry timeEntry = new TimeEntry();
            timeEntry.description = this.description;
            timeEntry.author = this.author;
            timeEntry.entryDate = LocalDate.now(); // UTC ?
            timeEntry.duration = Duration.parse(this.durationAsString);
            return Either.right(timeEntry);
        }
    }

    /**
     * Helper to display a Form with the field value in a Qute template
     * @return a Map of form field
     */
    @Override
    public final Map<String,String> getFieldValues(){
        Map<String,String> mapOfCurrentFields = new HashMap<>(3);
        mapOfCurrentFields.put("description", this.description);
        mapOfCurrentFields.put("author", this.author);
        mapOfCurrentFields.put("durationAsString", this.durationAsString);
        return Collections.unmodifiableMap(mapOfCurrentFields);
    }

}
