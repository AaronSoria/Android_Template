package com.enaretos.android_templete.rest.responses;

import android.text.TextUtils;

import com.enaretos.android_templete.models.ErrorModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import retrofit2.Response;

public class ApiResponse<T> {
    public static final int OK_STATUS = 200;
    public static final int ERROR_STATUS = 400;

    @SerializedName("statusCode")
    @Expose
    private int statusCode;

    @SerializedName("data")
    @Expose
    private T data;

    @SerializedName("error")
    @Expose
    private ErrorModel error;

    public ApiResponse() {
    }

    public ApiResponse(int statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public ApiResponse(int statusCode, ErrorModel error) {
        this.statusCode = statusCode;
        this.error = error;
    }

    public ApiResponse(Response<T> response) {
        statusCode = response.code();
        if(response.isSuccessful()) {
            data = response.body();
            error = null;
        } else {
            String message = null;
            if (response.errorBody() != null) {
                Gson gson = new GsonBuilder().create();
                try {
                    message = response.errorBody().string();
                    if(TextUtils.isEmpty(message)){
                        this.error = new ErrorModel("General Error");
                    }else{
                        ApiResponse resError = gson.fromJson(message, ApiResponse.class);
                        this.error = resError.error;
                    }
                } catch (Exception ignored) {
                    this.error = new ErrorModel(ignored.getMessage());
                }
            }
            if (message == null || message.trim().length() == 0) {
                message = response.message();
                this.error = new ErrorModel(message);
            }
            data = null;
        }
    }

    public boolean isSuccessful() {
        return statusCode >= 200 && statusCode < 300;
    }

    //**********GETS Y SETS***************

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ErrorModel getError() {
        return error;
    }

}