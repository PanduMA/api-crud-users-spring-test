package com.awda.model.response;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    HttpStatus httpStatus;
    String messages;
    public ErrorResponse(HttpStatus httpStatus, String messages){
        this.httpStatus = httpStatus;
        this.messages = messages;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessages() {
        return messages;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }
}
