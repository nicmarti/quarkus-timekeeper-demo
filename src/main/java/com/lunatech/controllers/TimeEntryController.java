package com.lunatech.controllers;

import com.lunatech.models.TimeEntry;
import com.lunatech.models.TimeEntryDTO;
import io.quarkus.qute.Engine;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.vavr.control.Either;
import org.jboss.resteasy.annotations.Form;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;

@Path("/times")
@Produces(MediaType.TEXT_HTML)
@ApplicationScoped
public class TimeEntryController {
    @Inject
    Template base;

    @Inject
    Template timeEntries;


    @Inject
    Template timeEntry;

    @Inject
    Template newTimeEntry;

    @Inject
    Engine engine;

    @GET
    public TemplateInstance list() {
        List<TimeEntry> entries = TimeEntry.listAll();
        return timeEntries.data("timeEntries", entries);
    }

    @GET
    @Path("/{id}")
    public TemplateInstance get(@PathParam("id") Long id) {
        TimeEntry entry = TimeEntry.findById(id);
        if (entry == null) {
            throw new WebApplicationException("This time entry does not exist", 404);
        }
        return timeEntry.data("timeEntry", entry);
    }

    @GET
    @Path("/new")
    public TemplateInstance prepareNew() {
        return newTimeEntry.instance();
    }

    @POST
    @Path("/new")
    @Consumes(APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response save(@Form TimeEntryDTO timeEntryDTO) {
        Either<String, TimeEntry> validTimeEntryOrError = timeEntryDTO.toValidTimeEntry();
        if (validTimeEntryOrError.isLeft()) {
            // A best practice is not to throw a WebApplicationException here, but to return a 400 Bad Request
            // with a clear error that should explain what is not correct in the Form
            // TODO what we really want is to display again the same template, with an Invalid Form.
            // It's not supported yet
            // To do so we need to create a Form<TimeEntry> and send it to the front view.
            return Response.status(400, validTimeEntryOrError.getLeft()).build();
        } else {
            TimeEntry newTimeEntry = validTimeEntryOrError.get();
            newTimeEntry.persist();
            return Response.seeOther(URI.create("/times")).build();
        }
    }

}
