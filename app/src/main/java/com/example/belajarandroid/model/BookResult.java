package com.example.belajarandroid.model;

import android.media.Image;

import com.example.belajarandroid.utils.Record;

public class BookResult {

    private boolean success;
    private Book record;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Book getRecord() {
        return record;
    }

    public void setRecord(Book record) {
        this.record = record;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BookResult(boolean success, Book record, String message) {
        this.success = success;
        this.record = record;
        this.message = message;
    }

    @Override
    public String toString() {
        return "BookResult{" +
                "success=" + success +
                ", record=" + record +
                ", message='" + message + '\'' +
                '}';
    }
}
