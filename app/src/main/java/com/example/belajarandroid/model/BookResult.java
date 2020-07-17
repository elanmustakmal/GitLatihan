package com.example.belajarandroid.model;

import android.media.Image;

import com.example.belajarandroid.utils.Record;

public class BookResult {

    private boolean success;
    private Record record;
    private String message;

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

    public BookResult(boolean success, Record record, String message) {
        this.success = success;
        this.record = record;
        this.message = message;
    }
}
