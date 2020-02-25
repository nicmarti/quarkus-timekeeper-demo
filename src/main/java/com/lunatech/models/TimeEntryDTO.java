package com.lunatech.models;

import org.lunatech.formidable.FormDTO;
import org.lunatech.formidable.FormFieldWithErrors;
import org.lunatech.formidable.Validatable;
import io.vavr.control.Either;

import javax.ws.rs.FormParam;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TimeEntryDTO implements Validatable<TimeEntry>, FormDTO {

    @FormParam("description")
    public String description;

    @FormParam("author")
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

    @Override
    public Either<FormFieldWithErrors, TimeEntry> valid() {

        FormFieldWithErrors errors = FormFieldWithErrors
                .prepareNew()
                .nonEmpty("description", description)
                .nonEmpty("author", author);

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

    public Map<String,String> getFieldValues(){
        Map<String,String> mapOfCurrentFields = new HashMap<>(3);
        mapOfCurrentFields.put("description", this.description);
        mapOfCurrentFields.put("author", this.author);
        mapOfCurrentFields.put("author", this.author);
        mapOfCurrentFields.put("durationAsString", this.durationAsString);
        return Collections.unmodifiableMap(mapOfCurrentFields);
    }

}
