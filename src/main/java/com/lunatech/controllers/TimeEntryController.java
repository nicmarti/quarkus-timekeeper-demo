package com.lunatech.controllers;

import com.lunatech.models.TimeEntry;
import io.quarkus.qute.Engine;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/times")
@ApplicationScoped
public class TimeEntryController {
    @Inject
    Template base;

    @Inject
    Template timeEntries;

    @Inject
    Engine engine;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance list() {
        List<TimeEntry> entries = TimeEntry.listAll();
        return timeEntries.data("timeEntries", entries);
    }

}
