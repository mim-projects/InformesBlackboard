package com.mimsoft.informesblackboard.application.utils.http.responses;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

public class ResponseHelper {
    public static Response Error(Status status, String error) {
        return Response.ok(ResponseString.error(error)).status(status).build();
    }

    public static Response ErrorMessage(Status status, String error) {
        return Response.ok(ResponseString.errorMessage(error)).status(status).build();
    }

    public static Response SuccessMessage(String key, String content) {
        return Response.ok(ResponseString.response(key, content)).build();
    }

    public static Response.ResponseBuilder DisableCORS(Response.ResponseBuilder response) {
        return response
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
    }

    public static Response SuccessObject(String content) {
        return Response.ok(ResponseString.responseObject("data", content)).build();
    }

    public static Response SuccessMessage(String content) {
        return Response.ok(ResponseString.response("data", content)).build();
    }
}