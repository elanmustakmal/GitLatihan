package com.example.belajarandroid.model;

import com.example.belajarandroid.utils.Record;

public class SignUpResult {
    private boolean success;
    private Record record;
    private String message;

    public SignUpResult() {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
