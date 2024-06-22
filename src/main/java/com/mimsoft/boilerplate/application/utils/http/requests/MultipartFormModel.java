package com.mimsoft.boilerplate.application.utils.http.requests;

import java.util.Arrays;

public class MultipartFormModel {
    private byte[] body;
    private String filename;
    private String format;

    public MultipartFormModel() {
    }

    public MultipartFormModel(byte[] body, String filename, String format) {
        this.body = body;
        this.filename = filename;
        this.format = format;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return "MultipartFormModel{" +
                "body=" + Arrays.toString(body) +
                ", filename='" + filename + '\'' +
                ", format='" + format + '\'' +
                '}';
    }
}