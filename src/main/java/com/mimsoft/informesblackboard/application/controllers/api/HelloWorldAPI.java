package com.mimsoft.informesblackboard.application.controllers.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("hello-world")
public class HelloWorldAPI {
    @GET
    public String helloWorld() {
        return "Hello World";
    }
}
