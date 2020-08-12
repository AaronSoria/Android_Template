package com.enaretos.android_templete.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorModel {
    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public ErrorModel() {
    }

    public ErrorModel(String message) {
        this.message = message;
    }

    //**********GETS Y SETS*************

    public void setMessage(String message) {
        this.message = message;
    }
}
