package com.lunatech.controllers;

import com.lunatech.models.TimeEntry;
import io.quarkus.qute.Engine;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

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
    Engine engine;

    @GET
    public TemplateInstance list() {
        List<TimeEntry> entries = TimeEntry.listAll();
        return timeEntries.data("timeEntries", entries);
    }

    @GET
    @Path("/{id}")
    public TemplateInstance get(@PathParam("id") Long id){
        TimeEntry entry = TimeEntry.findById(id);
        if(entry == null){
            throw new WebApplicationException("This time entry does not exist", 400);
        }
        return timeEntry.data("timeEntry", entry );
    }

}
