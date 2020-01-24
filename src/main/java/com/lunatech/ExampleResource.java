package com.lunatech;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/hello")
@Produces(MediaType.TEXT_PLAIN)
public class ExampleResource {
    @Inject
    Template hello;

    @GET
    public String hello() {
        return "hello";
    }

    @GET
    @Path("/{name}")
    public TemplateInstance get(@PathParam("name") String name){
        return hello.data("username",name);
    }

}