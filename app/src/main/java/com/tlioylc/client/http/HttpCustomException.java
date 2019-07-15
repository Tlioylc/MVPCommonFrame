package com.tlioylc.client.http;

public class HttpCustomException  extends Exception {
    public int code;
    public String message;
    public String body;

    public HttpCustomException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
