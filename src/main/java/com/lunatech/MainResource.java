package com.lunatech;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class MainResource {
    @Inject
    Template hello;

    @Inject
    Template index;

    @GET
    @Path("/")
    public TemplateInstance index() {
        return index.instance();
    }

    @GET
    @Path("/hello/{name}")
    public TemplateInstance get(@PathParam("name") String name) {
        return hello.data("username", name);
    }

}