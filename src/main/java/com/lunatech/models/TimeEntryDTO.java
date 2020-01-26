package com.lunatech.models;

import io.vavr.control.Either;

import javax.ws.rs.FormParam;
import java.time.Duration;
import java.time.LocalDate;

public class TimeEntryDTO {

    @FormParam("description")
    public String description;

    @FormParam("author")
    public String author;

    @FormParam("duration")
    public String durationAsString ;

    @Override
    public String toString() {
        return "TimeEntryDTO{" +
                "description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", durationAsString='" + durationAsString + '\'' +
                '}';
    }

 // See https://www.baeldung.com/vavr-either
    public Either<String, TimeEntry> toValidTimeEntry() {
        if (description == null || description.isEmpty()) {
            return Either.left("TimeEntry description cannot be empty");
        }
        if (author == null || author.isEmpty()) {
            return Either.left("Author is required");
        }

        TimeEntry timeEntry = new TimeEntry();
        timeEntry.description = this.description;
        timeEntry.author = this.author;
        timeEntry.entryDate = LocalDate.now(); // UTC ?
        timeEntry.duration = Duration.parse(this.durationAsString);
        return Either.right(timeEntry);

    }
}
