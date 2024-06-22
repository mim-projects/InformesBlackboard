package com.mimsoft.informesblackboard.application.utils.http.responses;

public class ResponseString {
    public static String response(String key, String content) {
        return "{\"" + key + "\": \"" + content + "\"}";
    }

    public static String responseObject(String key, String content) {
        return "{\"" + key + "\": " + content + "}";
    }

    public static String dataObject(String content) {
        return response("data", content);
    }

    public static String data(String content) {
        return response("data", content);
    }

    public static String data(Object content) {
        return response("data", String.valueOf(content));
    }

    public static String error(String content) {
        return response("error",  content);
    }

    public static String errorMessage(String content) {
        return error("{ message\":\"" + content + "\" }");
    }
}