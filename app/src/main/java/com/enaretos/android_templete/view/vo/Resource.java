package com.enaretos.android_templete.view.vo;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class Resource<T> {

    @NonNull
    public final Status status;

    @Nullable
    public final String message;

    @Nullable
    public final T data;

    @Nullable
    public final Integer statusCode;

    public Resource(@NonNull Status status, @Nullable T data, @Nullable String message, @Nullable Integer statusCode) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.statusCode = statusCode;
    }

    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(Status.SUCCESS, data, null,null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data, @Nullable Integer statusCode) {
        if (statusCode == 404 || statusCode == 400){
            return new Resource<>(Status.CONNECTION_ERROR, data, msg,statusCode);
        }
        if (statusCode == 401){
            return new Resource<>(Status.UNAUTHORIZED, data, msg,statusCode);
        }
        return new Resource<>(Status.API_ERROR, data, msg,statusCode);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(Status.LOADING, data, null,null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Resource<?> resource = (Resource<?>) o;

        if (status != resource.status) {
            return false;
        }
        if (!Objects.equals(message, resource.message)) {
            return false;
        }
        return Objects.equals(data, resource.data);
    }

    @Override
    public int hashCode() {
        int result = status.hashCode();
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
