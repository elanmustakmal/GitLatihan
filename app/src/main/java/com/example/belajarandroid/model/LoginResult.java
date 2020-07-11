package com.example.belajarandroid.model;


import com.example.belajarandroid.utils.Record;

public class LoginResult {

    private  boolean success;
    private Record record;
    private String token;

    public LoginResult() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
