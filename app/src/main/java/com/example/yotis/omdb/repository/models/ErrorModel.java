package com.example.yotis.omdb.repository.models;

/**
 * Created by lenny.becerra on 23/06/2016.
 */
public class ErrorModel {

    private int status;
    public String Message;

    public ErrorModel(int status, String message) {
        this.status = status;
        Message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
